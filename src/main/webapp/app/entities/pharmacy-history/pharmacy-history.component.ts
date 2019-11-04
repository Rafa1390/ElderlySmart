import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPharmacyHistory } from 'app/shared/model/pharmacy-history.model';
import { AccountService } from 'app/core/auth/account.service';
import { PharmacyHistoryService } from './pharmacy-history.service';

@Component({
  selector: 'jhi-pharmacy-history',
  templateUrl: './pharmacy-history.component.html'
})
export class PharmacyHistoryComponent implements OnInit, OnDestroy {
  pharmacyHistories: IPharmacyHistory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pharmacyHistoryService: PharmacyHistoryService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pharmacyHistoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IPharmacyHistory[]>) => res.ok),
        map((res: HttpResponse<IPharmacyHistory[]>) => res.body)
      )
      .subscribe((res: IPharmacyHistory[]) => {
        this.pharmacyHistories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPharmacyHistories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPharmacyHistory) {
    return item.id;
  }

  registerChangeInPharmacyHistories() {
    this.eventSubscriber = this.eventManager.subscribe('pharmacyHistoryListModification', response => this.loadAll());
  }
}
