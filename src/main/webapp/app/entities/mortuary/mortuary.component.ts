import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IMortuary } from 'app/shared/model/mortuary.model';
import { AccountService } from 'app/core/auth/account.service';
import { MortuaryService } from './mortuary.service';

@Component({
  selector: 'jhi-mortuary',
  templateUrl: './mortuary.component.html'
})
export class MortuaryComponent implements OnInit, OnDestroy {
  mortuaries: IMortuary[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected mortuaryService: MortuaryService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.mortuaryService
      .query()
      .pipe(
        filter((res: HttpResponse<IMortuary[]>) => res.ok),
        map((res: HttpResponse<IMortuary[]>) => res.body)
      )
      .subscribe((res: IMortuary[]) => {
        this.mortuaries = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMortuaries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMortuary) {
    return item.id;
  }

  registerChangeInMortuaries() {
    this.eventSubscriber = this.eventManager.subscribe('mortuaryListModification', response => this.loadAll());
  }
}
