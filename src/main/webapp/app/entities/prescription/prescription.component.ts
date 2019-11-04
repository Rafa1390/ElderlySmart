import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPrescription } from 'app/shared/model/prescription.model';
import { AccountService } from 'app/core/auth/account.service';
import { PrescriptionService } from './prescription.service';

@Component({
  selector: 'jhi-prescription',
  templateUrl: './prescription.component.html'
})
export class PrescriptionComponent implements OnInit, OnDestroy {
  prescriptions: IPrescription[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected prescriptionService: PrescriptionService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.prescriptionService
      .query()
      .pipe(
        filter((res: HttpResponse<IPrescription[]>) => res.ok),
        map((res: HttpResponse<IPrescription[]>) => res.body)
      )
      .subscribe((res: IPrescription[]) => {
        this.prescriptions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPrescriptions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPrescription) {
    return item.id;
  }

  registerChangeInPrescriptions() {
    this.eventSubscriber = this.eventManager.subscribe('prescriptionListModification', response => this.loadAll());
  }
}
