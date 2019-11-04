import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMedicalAppointment, MedicalAppointment } from 'app/shared/model/medical-appointment.model';
import { MedicalAppointmentService } from './medical-appointment.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';

@Component({
  selector: 'jhi-medical-appointment-update',
  templateUrl: './medical-appointment-update.component.html'
})
export class MedicalAppointmentUpdateComponent implements OnInit {
  isSaving: boolean;

  employees: IEmployee[];

  doctors: IDoctor[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    idMedicalAppointment: [],
    date: [],
    time: [],
    state: [],
    employee: [],
    doctor: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected medicalAppointmentService: MedicalAppointmentService,
    protected employeeService: EmployeeService,
    protected doctorService: DoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicalAppointment }) => {
      this.updateForm(medicalAppointment);
    });
    this.employeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmployee[]>) => response.body)
      )
      .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.doctorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDoctor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDoctor[]>) => response.body)
      )
      .subscribe((res: IDoctor[]) => (this.doctors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(medicalAppointment: IMedicalAppointment) {
    this.editForm.patchValue({
      id: medicalAppointment.id,
      idMedicalAppointment: medicalAppointment.idMedicalAppointment,
      date: medicalAppointment.date,
      time: medicalAppointment.time,
      state: medicalAppointment.state,
      employee: medicalAppointment.employee,
      doctor: medicalAppointment.doctor
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicalAppointment = this.createFromForm();
    if (medicalAppointment.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalAppointmentService.update(medicalAppointment));
    } else {
      this.subscribeToSaveResponse(this.medicalAppointmentService.create(medicalAppointment));
    }
  }

  private createFromForm(): IMedicalAppointment {
    return {
      ...new MedicalAppointment(),
      id: this.editForm.get(['id']).value,
      idMedicalAppointment: this.editForm.get(['idMedicalAppointment']).value,
      date: this.editForm.get(['date']).value,
      time: this.editForm.get(['time']).value,
      state: this.editForm.get(['state']).value,
      employee: this.editForm.get(['employee']).value,
      doctor: this.editForm.get(['doctor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalAppointment>>) {
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

  trackEmployeeById(index: number, item: IEmployee) {
    return item.id;
  }

  trackDoctorById(index: number, item: IDoctor) {
    return item.id;
  }
}
