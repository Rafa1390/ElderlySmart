import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPrescription, Prescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';
import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';

@Component({
  selector: 'jhi-prescription-update',
  templateUrl: './prescription-update.component.html'
})
export class PrescriptionUpdateComponent implements OnInit {
  isSaving: boolean;

  pharmacies: IPharmacy[];

  doctors: IDoctor[];
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    idPrescription: new FormControl('', [Validators.required]),
    officeName: new FormControl('', [Validators.required]),
    creationDate: [],
    doctorName: new FormControl('', [Validators.required]),
    patientName: new FormControl('', [Validators.required]),
    patientAge: new FormControl('', [Validators.required]),
    prescriptionDrugs: new FormControl('', [Validators.required]),
    state: new FormControl('', [Validators.required]),
    pharmacy: [],
    doctor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected prescriptionService: PrescriptionService,
    protected pharmacyService: PharmacyService,
    protected doctorService: DoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prescription }) => {
      this.updateForm(prescription);
    });
    this.pharmacyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPharmacy[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPharmacy[]>) => response.body)
      )
      .subscribe((res: IPharmacy[]) => (this.pharmacies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.doctorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDoctor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDoctor[]>) => response.body)
      )
      .subscribe((res: IDoctor[]) => (this.doctors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(prescription: IPrescription) {
    this.editForm.patchValue({
      id: prescription.id,
      idPrescription: prescription.idPrescription,
      officeName: prescription.officeName,
      creationDate: prescription.creationDate,
      doctorName: prescription.doctorName,
      patientName: prescription.patientName,
      patientAge: prescription.patientAge,
      prescriptionDrugs: prescription.prescriptionDrugs,
      state: prescription.state,
      pharmacy: prescription.pharmacy,
      doctor: prescription.doctor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const prescription = this.createFromForm();
    if (prescription.id !== undefined) {
      this.subscribeToSaveResponse(this.prescriptionService.update(prescription));
    } else {
      this.subscribeToSaveResponse(this.prescriptionService.create(prescription));
    }
  }

  private createFromForm(): IPrescription {
    return {
      ...new Prescription(),
      id: this.editForm.get(['id']).value,
      idPrescription: this.editForm.get(['idPrescription']).value,
      officeName: this.editForm.get(['officeName']).value,
      creationDate: this.editForm.get(['creationDate']).value,
      doctorName: this.editForm.get(['doctorName']).value,
      patientName: this.editForm.get(['patientName']).value,
      patientAge: this.editForm.get(['patientAge']).value,
      prescriptionDrugs: this.editForm.get(['prescriptionDrugs']).value,
      state: this.editForm.get(['state']).value,
      pharmacy: this.editForm.get(['pharmacy']).value,
      doctor: this.editForm.get(['doctor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrescription>>) {
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

  trackPharmacyById(index: number, item: IPharmacy) {
    return item.id;
  }

  trackDoctorById(index: number, item: IDoctor) {
    return item.id;
  }

  get idPrescription() {
    return this.editForm.get('idPrescription');
  }

  get officeName() {
    return this.editForm.get('officeName');
  }

  get doctorName() {
    return this.editForm.get('doctorName');
  }

  get patientName() {
    return this.editForm.get('patientName');
  }

  get patientAge() {
    return this.editForm.get('patientAge');
  }

  get prescriptionDrugs() {
    return this.editForm.get('prescriptionDrugs');
  }

  get state() {
    return this.editForm.get('state');
  }

  get email() {
    return this.editForm.get('email');
  }
}
