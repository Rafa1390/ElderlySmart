import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProduct } from 'app/shared/model/product.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductService } from './product.service';

@Component({
  selector: 'jhi-product',
  templateUrl: './product.component.html'
})
export class ProductComponent implements OnInit, OnDestroy {
  products: IProduct[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productService: ProductService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productService
      .query()
      .pipe(
        filter((res: HttpResponse<IProduct[]>) => res.ok),
        map((res: HttpResponse<IProduct[]>) => res.body)
      )
      .subscribe((res: IProduct[]) => {
        this.products = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProducts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProduct) {
    return item.id;
  }

  registerChangeInProducts() {
    this.eventSubscriber = this.eventManager.subscribe('productListModification', response => this.loadAll());
  }
}
