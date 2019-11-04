import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicationSchedule } from 'app/shared/model/medication-schedule.model';
import { AccountService } from 'app/core/auth/account.service';
import { MedicationScheduleService } from './medication-schedule.service';

@Component({
  selector: 'jhi-medication-schedule',
  templateUrl: './medication-schedule.component.html'
})
export class MedicationScheduleComponent implements OnInit, OnDestroy {
  medicationSchedules: IMedicationSchedule[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected medicationScheduleService: MedicationScheduleService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.medicationScheduleService
      .query()
      .pipe(
        filter((res: HttpResponse<IMedicationSchedule[]>) => res.ok),
        map((res: HttpResponse<IMedicationSchedule[]>) => res.body)
      )
      .subscribe((res: IMedicationSchedule[]) => {
        this.medicationSchedules = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMedicationSchedules();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedicationSchedule) {
    return item.id;
  }

  registerChangeInMedicationSchedules() {
    this.eventSubscriber = this.eventManager.subscribe('medicationScheduleListModification', response => this.loadAll());
  }
}
