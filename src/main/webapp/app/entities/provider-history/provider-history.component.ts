import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProviderHistory } from 'app/shared/model/provider-history.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProviderHistoryService } from './provider-history.service';

@Component({
  selector: 'jhi-provider-history',
  templateUrl: './provider-history.component.html'
})
export class ProviderHistoryComponent implements OnInit, OnDestroy {
  providerHistories: IProviderHistory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected providerHistoryService: ProviderHistoryService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.providerHistoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IProviderHistory[]>) => res.ok),
        map((res: HttpResponse<IProviderHistory[]>) => res.body)
      )
      .subscribe((res: IProviderHistory[]) => {
        this.providerHistories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProviderHistories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProviderHistory) {
    return item.id;
  }

  registerChangeInProviderHistories() {
    this.eventSubscriber = this.eventManager.subscribe('providerHistoryListModification', response => this.loadAll());
  }
}
