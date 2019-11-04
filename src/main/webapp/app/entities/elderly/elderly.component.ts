import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IElderly } from 'app/shared/model/elderly.model';
import { AccountService } from 'app/core/auth/account.service';
import { ElderlyService } from './elderly.service';

@Component({
  selector: 'jhi-elderly',
  templateUrl: './elderly.component.html'
})
export class ElderlyComponent implements OnInit, OnDestroy {
  elderlies: IElderly[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected elderlyService: ElderlyService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.elderlyService
      .query()
      .pipe(
        filter((res: HttpResponse<IElderly[]>) => res.ok),
        map((res: HttpResponse<IElderly[]>) => res.body)
      )
      .subscribe((res: IElderly[]) => {
        this.elderlies = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInElderlies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IElderly) {
    return item.id;
  }

  registerChangeInElderlies() {
    this.eventSubscriber = this.eventManager.subscribe('elderlyListModification', response => this.loadAll());
  }
}
