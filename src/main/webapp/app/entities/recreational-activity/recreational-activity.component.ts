import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IRecreationalActivity } from 'app/shared/model/recreational-activity.model';
import { AccountService } from 'app/core/auth/account.service';
import { RecreationalActivityService } from './recreational-activity.service';
import { FullCalendarComponent } from '@fullcalendar/angular';
import { EventInput } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGrigPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import Swal from 'sweetalert2';

@Component({
  selector: 'jhi-recreational-activity',
  templateUrl: './recreational-activity.component.html'
})
export class RecreationalActivityComponent implements OnInit, OnDestroy {
  recreationalActivities: IRecreationalActivity[];
  currentAccount: any;
  eventSubscriber: Subscription;
  calendarEvents: EventInput[] = [];
  calendarVisible = true;
  calendarPlugins = [dayGridPlugin, timeGrigPlugin, interactionPlugin];
  calendarWeekends = true;

  constructor(
    protected recreationalActivityService: RecreationalActivityService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.recreationalActivityService
      .query()
      .pipe(
        filter((res: HttpResponse<IRecreationalActivity[]>) => res.ok),
        map((res: HttpResponse<IRecreationalActivity[]>) => res.body)
      )
      .subscribe((res: IRecreationalActivity[]) => {
        this.recreationalActivities = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.loadCalendar();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRecreationalActivities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRecreationalActivity) {
    return item.id;
  }

  registerChangeInRecreationalActivities() {
    this.eventSubscriber = this.eventManager.subscribe('recreationalActivityListModification', response => this.loadAll());
  }

  loadCalendar() {
    this.recreationalActivityService.getData().subscribe(response => {
      for (let i = 0; i < response.length; i++) {
        this.calendarEvents = this.calendarEvents.concat({
          id: response[i].id,
          title: response[i].name,
          start: response[i].date.toString()
        });
      }
    });
  }

  eventClick(arg) {
    Swal.fire({
      title: arg.event.title,
      html:
        '<div class="form-group">' +
        '<div class="btn-group"> ' +
        '<button type="submit" [routerLink]="/recreational-activity,' +
        arg.event.id +
        ',view" class="btn btn-success">' +
        '<fa-icon [icon]="\'eye\'"></fa-icon>' +
        '<span class="d-none d-md-inline">Ver</span>' +
        '</button>' +
        '</div>' +
        '</div>'
    });
  }
}
