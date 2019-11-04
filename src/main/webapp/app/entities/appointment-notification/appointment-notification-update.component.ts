import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAppointmentNotification, AppointmentNotification } from 'app/shared/model/appointment-notification.model';
import { AppointmentNotificationService } from './appointment-notification.service';
import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';
import { MedicalAppointmentService } from 'app/entities/medical-appointment/medical-appointment.service';

@Component({
  selector: 'jhi-appointment-notification-update',
  templateUrl: './appointment-notification-update.component.html'
})
export class AppointmentNotificationUpdateComponent implements OnInit {
  isSaving: boolean;

  medicalappointments: IMedicalAppointment[];

  editForm = this.fb.group({
    id: [],
    idAppointmentNotification: [],
    description: [],
    state: [],
    medicalAppointment: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected appointmentNotificationService: AppointmentNotificationService,
    protected medicalAppointmentService: MedicalAppointmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ appointmentNotification }) => {
      this.updateForm(appointmentNotification);
    });
    this.medicalAppointmentService
      .query({ filter: 'appointmentnotification-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IMedicalAppointment[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMedicalAppointment[]>) => response.body)
      )
      .subscribe(
        (res: IMedicalAppointment[]) => {
          if (!this.editForm.get('medicalAppointment').value || !this.editForm.get('medicalAppointment').value.id) {
            this.medicalappointments = res;
          } else {
            this.medicalAppointmentService
              .find(this.editForm.get('medicalAppointment').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IMedicalAppointment>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IMedicalAppointment>) => subResponse.body)
              )
              .subscribe(
                (subRes: IMedicalAppointment) => (this.medicalappointments = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(appointmentNotification: IAppointmentNotification) {
    this.editForm.patchValue({
      id: appointmentNotification.id,
      idAppointmentNotification: appointmentNotification.idAppointmentNotification,
      description: appointmentNotification.description,
      state: appointmentNotification.state,
      medicalAppointment: appointmentNotification.medicalAppointment
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const appointmentNotification = this.createFromForm();
    if (appointmentNotification.id !== undefined) {
      this.subscribeToSaveResponse(this.appointmentNotificationService.update(appointmentNotification));
    } else {
      this.subscribeToSaveResponse(this.appointmentNotificationService.create(appointmentNotification));
    }
  }

  private createFromForm(): IAppointmentNotification {
    return {
      ...new AppointmentNotification(),
      id: this.editForm.get(['id']).value,
      idAppointmentNotification: this.editForm.get(['idAppointmentNotification']).value,
      description: this.editForm.get(['description']).value,
      state: this.editForm.get(['state']).value,
      medicalAppointment: this.editForm.get(['medicalAppointment']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppointmentNotification>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackMedicalAppointmentById(index: number, item: IMedicalAppointment) {
    return item.id;
  }
}
