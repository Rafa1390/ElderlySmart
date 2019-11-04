import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IRecreationalActivity } from 'app/shared/model/recreational-activity.model';
import { AccountService } from 'app/core/auth/account.service';
import { RecreationalActivityService } from './recreational-activity.service';

@Component({
  selector: 'jhi-recreational-activity',
  templateUrl: './recreational-activity.component.html'
})
export class RecreationalActivityComponent implements OnInit, OnDestroy {
  recreationalActivities: IRecreationalActivity[];
  currentAccount: any;
  eventSubscriber: Subscription;

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
}
