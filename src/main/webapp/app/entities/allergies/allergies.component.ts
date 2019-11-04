import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAllergies } from 'app/shared/model/allergies.model';
import { AccountService } from 'app/core/auth/account.service';
import { AllergiesService } from './allergies.service';

@Component({
  selector: 'jhi-allergies',
  templateUrl: './allergies.component.html'
})
export class AllergiesComponent implements OnInit, OnDestroy {
  allergies: IAllergies[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected allergiesService: AllergiesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.allergiesService
      .query()
      .pipe(
        filter((res: HttpResponse<IAllergies[]>) => res.ok),
        map((res: HttpResponse<IAllergies[]>) => res.body)
      )
      .subscribe((res: IAllergies[]) => {
        this.allergies = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAllergies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAllergies) {
    return item.id;
  }

  registerChangeInAllergies() {
    this.eventSubscriber = this.eventManager.subscribe('allergiesListModification', response => this.loadAll());
  }
}
