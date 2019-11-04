import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAppointmentNotification } from 'app/shared/model/appointment-notification.model';
import { AccountService } from 'app/core/auth/account.service';
import { AppointmentNotificationService } from './appointment-notification.service';

@Component({
  selector: 'jhi-appointment-notification',
  templateUrl: './appointment-notification.component.html'
})
export class AppointmentNotificationComponent implements OnInit, OnDestroy {
  appointmentNotifications: IAppointmentNotification[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected appointmentNotificationService: AppointmentNotificationService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.appointmentNotificationService
      .query()
      .pipe(
        filter((res: HttpResponse<IAppointmentNotification[]>) => res.ok),
        map((res: HttpResponse<IAppointmentNotification[]>) => res.body)
      )
      .subscribe((res: IAppointmentNotification[]) => {
        this.appointmentNotifications = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAppointmentNotifications();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAppointmentNotification) {
    return item.id;
  }

  registerChangeInAppointmentNotifications() {
    this.eventSubscriber = this.eventManager.subscribe('appointmentNotificationListModification', response => this.loadAll());
  }
}
