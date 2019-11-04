import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';
import { AccountService } from 'app/core/auth/account.service';
import { InventoryPharmacyService } from './inventory-pharmacy.service';

@Component({
  selector: 'jhi-inventory-pharmacy',
  templateUrl: './inventory-pharmacy.component.html'
})
export class InventoryPharmacyComponent implements OnInit, OnDestroy {
  inventoryPharmacies: IInventoryPharmacy[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected inventoryPharmacyService: InventoryPharmacyService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.inventoryPharmacyService
      .query()
      .pipe(
        filter((res: HttpResponse<IInventoryPharmacy[]>) => res.ok),
        map((res: HttpResponse<IInventoryPharmacy[]>) => res.body)
      )
      .subscribe((res: IInventoryPharmacy[]) => {
        this.inventoryPharmacies = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInventoryPharmacies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInventoryPharmacy) {
    return item.id;
  }

  registerChangeInInventoryPharmacies() {
    this.eventSubscriber = this.eventManager.subscribe('inventoryPharmacyListModification', response => this.loadAll());
  }
}
