import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAsylum } from 'app/shared/model/asylum.model';
import { AccountService } from 'app/core/auth/account.service';
import { AsylumService } from './asylum.service';

@Component({
  selector: 'jhi-asylum',
  templateUrl: './asylum.component.html'
})
export class AsylumComponent implements OnInit, OnDestroy {
  asylums: IAsylum[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected asylumService: AsylumService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.asylumService
      .query()
      .pipe(
        filter((res: HttpResponse<IAsylum[]>) => res.ok),
        map((res: HttpResponse<IAsylum[]>) => res.body)
      )
      .subscribe((res: IAsylum[]) => {
        this.asylums = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAsylums();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAsylum) {
    return item.id;
  }

  registerChangeInAsylums() {
    this.eventSubscriber = this.eventManager.subscribe('asylumListModification', response => this.loadAll());
  }
}
