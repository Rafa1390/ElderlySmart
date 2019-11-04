import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IFamily } from 'app/shared/model/family.model';
import { AccountService } from 'app/core/auth/account.service';
import { FamilyService } from './family.service';

@Component({
  selector: 'jhi-family',
  templateUrl: './family.component.html'
})
export class FamilyComponent implements OnInit, OnDestroy {
  families: IFamily[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected familyService: FamilyService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.familyService
      .query()
      .pipe(
        filter((res: HttpResponse<IFamily[]>) => res.ok),
        map((res: HttpResponse<IFamily[]>) => res.body)
      )
      .subscribe((res: IFamily[]) => {
        this.families = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFamilies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFamily) {
    return item.id;
  }

  registerChangeInFamilies() {
    this.eventSubscriber = this.eventManager.subscribe('familyListModification', response => this.loadAll());
  }
}
