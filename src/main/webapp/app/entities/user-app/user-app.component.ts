import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IUserApp } from 'app/shared/model/user-app.model';
import { AccountService } from 'app/core/auth/account.service';
import { UserAppService } from './user-app.service';

@Component({
  selector: 'jhi-user-app',
  templateUrl: './user-app.component.html'
})
export class UserAppComponent implements OnInit, OnDestroy {
  userApps: IUserApp[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected userAppService: UserAppService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.userAppService
      .query()
      .pipe(
        filter((res: HttpResponse<IUserApp[]>) => res.ok),
        map((res: HttpResponse<IUserApp[]>) => res.body)
      )
      .subscribe((res: IUserApp[]) => {
        this.userApps = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserApps();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserApp) {
    return item.id;
  }

  registerChangeInUserApps() {
    this.eventSubscriber = this.eventManager.subscribe('userAppListModification', response => this.loadAll());
  }
}
