import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryProvider } from 'app/shared/model/inventory-provider.model';
import { AccountService } from 'app/core/auth/account.service';
import { InventoryProviderService } from './inventory-provider.service';

@Component({
  selector: 'jhi-inventory-provider',
  templateUrl: './inventory-provider.component.html'
})
export class InventoryProviderComponent implements OnInit, OnDestroy {
  inventoryProviders: IInventoryProvider[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected inventoryProviderService: InventoryProviderService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.inventoryProviderService
      .query()
      .pipe(
        filter((res: HttpResponse<IInventoryProvider[]>) => res.ok),
        map((res: HttpResponse<IInventoryProvider[]>) => res.body)
      )
      .subscribe((res: IInventoryProvider[]) => {
        this.inventoryProviders = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInventoryProviders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInventoryProvider) {
    return item.id;
  }

  registerChangeInInventoryProviders() {
    this.eventSubscriber = this.eventManager.subscribe('inventoryProviderListModification', response => this.loadAll());
  }
}
