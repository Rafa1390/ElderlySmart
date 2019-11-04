import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicament } from 'app/shared/model/medicament.model';
import { AccountService } from 'app/core/auth/account.service';
import { MedicamentService } from './medicament.service';

@Component({
  selector: 'jhi-medicament',
  templateUrl: './medicament.component.html'
})
export class MedicamentComponent implements OnInit, OnDestroy {
  medicaments: IMedicament[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected medicamentService: MedicamentService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.medicamentService
      .query()
      .pipe(
        filter((res: HttpResponse<IMedicament[]>) => res.ok),
        map((res: HttpResponse<IMedicament[]>) => res.body)
      )
      .subscribe((res: IMedicament[]) => {
        this.medicaments = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMedicaments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedicament) {
    return item.id;
  }

  registerChangeInMedicaments() {
    this.eventSubscriber = this.eventManager.subscribe('medicamentListModification', response => this.loadAll());
  }
}
