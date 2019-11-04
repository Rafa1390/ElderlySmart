import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { AccountService } from 'app/core/auth/account.service';
import { PharmacyService } from './pharmacy.service';

@Component({
  selector: 'jhi-pharmacy',
  templateUrl: './pharmacy.component.html'
})
export class PharmacyComponent implements OnInit, OnDestroy {
  pharmacies: IPharmacy[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pharmacyService: PharmacyService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pharmacyService
      .query()
      .pipe(
        filter((res: HttpResponse<IPharmacy[]>) => res.ok),
        map((res: HttpResponse<IPharmacy[]>) => res.body)
      )
      .subscribe((res: IPharmacy[]) => {
        this.pharmacies = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPharmacies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPharmacy) {
    return item.id;
  }

  registerChangeInPharmacies() {
    this.eventSubscriber = this.eventManager.subscribe('pharmacyListModification', response => this.loadAll());
  }
}
