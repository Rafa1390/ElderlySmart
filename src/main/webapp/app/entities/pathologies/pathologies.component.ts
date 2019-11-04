import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPathologies } from 'app/shared/model/pathologies.model';
import { AccountService } from 'app/core/auth/account.service';
import { PathologiesService } from './pathologies.service';

@Component({
  selector: 'jhi-pathologies',
  templateUrl: './pathologies.component.html'
})
export class PathologiesComponent implements OnInit, OnDestroy {
  pathologies: IPathologies[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pathologiesService: PathologiesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pathologiesService
      .query()
      .pipe(
        filter((res: HttpResponse<IPathologies[]>) => res.ok),
        map((res: HttpResponse<IPathologies[]>) => res.body)
      )
      .subscribe((res: IPathologies[]) => {
        this.pathologies = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPathologies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPathologies) {
    return item.id;
  }

  registerChangeInPathologies() {
    this.eventSubscriber = this.eventManager.subscribe('pathologiesListModification', response => this.loadAll());
  }
}
