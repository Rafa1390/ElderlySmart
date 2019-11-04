import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodMenu } from 'app/shared/model/food-menu.model';
import { AccountService } from 'app/core/auth/account.service';
import { FoodMenuService } from './food-menu.service';

@Component({
  selector: 'jhi-food-menu',
  templateUrl: './food-menu.component.html'
})
export class FoodMenuComponent implements OnInit, OnDestroy {
  foodMenus: IFoodMenu[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected foodMenuService: FoodMenuService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.foodMenuService
      .query()
      .pipe(
        filter((res: HttpResponse<IFoodMenu[]>) => res.ok),
        map((res: HttpResponse<IFoodMenu[]>) => res.body)
      )
      .subscribe((res: IFoodMenu[]) => {
        this.foodMenus = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFoodMenus();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFoodMenu) {
    return item.id;
  }

  registerChangeInFoodMenus() {
    this.eventSubscriber = this.eventManager.subscribe('foodMenuListModification', response => this.loadAll());
  }
}
