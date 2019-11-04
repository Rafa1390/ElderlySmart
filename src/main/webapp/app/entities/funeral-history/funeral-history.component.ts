import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IFuneralHistory } from 'app/shared/model/funeral-history.model';
import { AccountService } from 'app/core/auth/account.service';
import { FuneralHistoryService } from './funeral-history.service';

@Component({
  selector: 'jhi-funeral-history',
  templateUrl: './funeral-history.component.html'
})
export class FuneralHistoryComponent implements OnInit, OnDestroy {
  funeralHistories: IFuneralHistory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected funeralHistoryService: FuneralHistoryService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.funeralHistoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IFuneralHistory[]>) => res.ok),
        map((res: HttpResponse<IFuneralHistory[]>) => res.body)
      )
      .subscribe((res: IFuneralHistory[]) => {
        this.funeralHistories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFuneralHistories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFuneralHistory) {
    return item.id;
  }

  registerChangeInFuneralHistories() {
    this.eventSubscriber = this.eventManager.subscribe('funeralHistoryListModification', response => this.loadAll());
  }
}
