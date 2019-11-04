import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IDonationHistory } from 'app/shared/model/donation-history.model';
import { AccountService } from 'app/core/auth/account.service';
import { DonationHistoryService } from './donation-history.service';

@Component({
  selector: 'jhi-donation-history',
  templateUrl: './donation-history.component.html'
})
export class DonationHistoryComponent implements OnInit, OnDestroy {
  donationHistories: IDonationHistory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected donationHistoryService: DonationHistoryService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.donationHistoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IDonationHistory[]>) => res.ok),
        map((res: HttpResponse<IDonationHistory[]>) => res.body)
      )
      .subscribe((res: IDonationHistory[]) => {
        this.donationHistories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDonationHistories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDonationHistory) {
    return item.id;
  }

  registerChangeInDonationHistories() {
    this.eventSubscriber = this.eventManager.subscribe('donationHistoryListModification', response => this.loadAll());
  }
}
