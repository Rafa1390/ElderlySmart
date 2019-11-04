import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPrescriptionNotification, PrescriptionNotification } from 'app/shared/model/prescription-notification.model';
import { PrescriptionNotificationService } from './prescription-notification.service';
import { IPrescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from 'app/entities/prescription/prescription.service';

@Component({
  selector: 'jhi-prescription-notification-update',
  templateUrl: './prescription-notification-update.component.html'
})
export class PrescriptionNotificationUpdateComponent implements OnInit {
  isSaving: boolean;

  prescriptions: IPrescription[];

  editForm = this.fb.group({
    id: [],
    idPrescriptionNotification: [],
    description: [],
    state: [],
    prescription: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected prescriptionNotificationService: PrescriptionNotificationService,
    protected prescriptionService: PrescriptionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prescriptionNotification }) => {
      this.updateForm(prescriptionNotification);
    });
    this.prescriptionService
      .query({ filter: 'prescriptionnotification-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPrescription[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPrescription[]>) => response.body)
      )
      .subscribe(
        (res: IPrescription[]) => {
          if (!this.editForm.get('prescription').value || !this.editForm.get('prescription').value.id) {
            this.prescriptions = res;
          } else {
            this.prescriptionService
              .find(this.editForm.get('prescription').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPrescription>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPrescription>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPrescription) => (this.prescriptions = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(prescriptionNotification: IPrescriptionNotification) {
    this.editForm.patchValue({
      id: prescriptionNotification.id,
      idPrescriptionNotification: prescriptionNotification.idPrescriptionNotification,
      description: prescriptionNotification.description,
      state: prescriptionNotification.state,
      prescription: prescriptionNotification.prescription
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const prescriptionNotification = this.createFromForm();
    if (prescriptionNotification.id !== undefined) {
      this.subscribeToSaveResponse(this.prescriptionNotificationService.update(prescriptionNotification));
    } else {
      this.subscribeToSaveResponse(this.prescriptionNotificationService.create(prescriptionNotification));
    }
  }

  private createFromForm(): IPrescriptionNotification {
    return {
      ...new PrescriptionNotification(),
      id: this.editForm.get(['id']).value,
      idPrescriptionNotification: this.editForm.get(['idPrescriptionNotification']).value,
      description: this.editForm.get(['description']).value,
      state: this.editForm.get(['state']).value,
      prescription: this.editForm.get(['prescription']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrescriptionNotification>>) {
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

  trackPrescriptionById(index: number, item: IPrescription) {
    return item.id;
  }
}
