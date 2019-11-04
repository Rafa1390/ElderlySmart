import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IFuneralPackages } from 'app/shared/model/funeral-packages.model';
import { AccountService } from 'app/core/auth/account.service';
import { FuneralPackagesService } from './funeral-packages.service';

@Component({
  selector: 'jhi-funeral-packages',
  templateUrl: './funeral-packages.component.html'
})
export class FuneralPackagesComponent implements OnInit, OnDestroy {
  funeralPackages: IFuneralPackages[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected funeralPackagesService: FuneralPackagesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.funeralPackagesService
      .query()
      .pipe(
        filter((res: HttpResponse<IFuneralPackages[]>) => res.ok),
        map((res: HttpResponse<IFuneralPackages[]>) => res.body)
      )
      .subscribe((res: IFuneralPackages[]) => {
        this.funeralPackages = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFuneralPackages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFuneralPackages) {
    return item.id;
  }

  registerChangeInFuneralPackages() {
    this.eventSubscriber = this.eventManager.subscribe('funeralPackagesListModification', response => this.loadAll());
  }
}
