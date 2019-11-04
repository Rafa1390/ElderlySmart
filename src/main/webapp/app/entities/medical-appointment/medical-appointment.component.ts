import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';
import { AccountService } from 'app/core/auth/account.service';
import { MedicalAppointmentService } from './medical-appointment.service';

@Component({
  selector: 'jhi-medical-appointment',
  templateUrl: './medical-appointment.component.html'
})
export class MedicalAppointmentComponent implements OnInit, OnDestroy {
  medicalAppointments: IMedicalAppointment[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected medicalAppointmentService: MedicalAppointmentService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.medicalAppointmentService
      .query()
      .pipe(
        filter((res: HttpResponse<IMedicalAppointment[]>) => res.ok),
        map((res: HttpResponse<IMedicalAppointment[]>) => res.body)
      )
      .subscribe((res: IMedicalAppointment[]) => {
        this.medicalAppointments = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMedicalAppointments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedicalAppointment) {
    return item.id;
  }

  registerChangeInMedicalAppointments() {
    this.eventSubscriber = this.eventManager.subscribe('medicalAppointmentListModification', response => this.loadAll());
  }
}
