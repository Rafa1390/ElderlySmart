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
import { IElderly, Elderly } from 'app/shared/model/elderly.model';
import { ElderlyService } from './elderly.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';
import { IFamily } from 'app/shared/model/family.model';
import { FamilyService } from 'app/entities/family/family.service';
import { IDoctor } from 'app/shared/model/doctor.model';
import { DoctorService } from 'app/entities/doctor/doctor.service';

@Component({
  selector: 'jhi-elderly-update',
  templateUrl: './elderly-update.component.html'
})
export class ElderlyUpdateComponent implements OnInit {
  isSaving: boolean;

  employees: IEmployee[];

  userapps: IUserApp[];

  families: IFamily[];

  doctors: IDoctor[];
  admissionDateDp: any;

  editForm = this.fb.group({
    id: [],
    idElderly: [],
    nationality: [],
    address: [],
    admissionDate: [],
    state: [],
    employee: [],
    userApp: [],
    families: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected elderlyService: ElderlyService,
    protected employeeService: EmployeeService,
    protected userAppService: UserAppService,
    protected familyService: FamilyService,
    protected doctorService: DoctorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ elderly }) => {
      this.updateForm(elderly);
    });
    this.employeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmployee[]>) => response.body)
      )
      .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userAppService
      .query({ filter: 'elderly-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IUserApp[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserApp[]>) => response.body)
      )
      .subscribe(
        (res: IUserApp[]) => {
          if (!this.editForm.get('userApp').value || !this.editForm.get('userApp').value.id) {
            this.userapps = res;
          } else {
            this.userAppService
              .find(this.editForm.get('userApp').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IUserApp>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IUserApp>) => subResponse.body)
              )
              .subscribe(
                (subRes: IUserApp) => (this.userapps = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.familyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFamily[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFamily[]>) => response.body)
      )
      .subscribe((res: IFamily[]) => (this.families = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.doctorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDoctor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDoctor[]>) => response.body)
      )
      .subscribe((res: IDoctor[]) => (this.doctors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(elderly: IElderly) {
    this.editForm.patchValue({
      id: elderly.id,
      idElderly: elderly.idElderly,
      nationality: elderly.nationality,
      address: elderly.address,
      admissionDate: elderly.admissionDate,
      state: elderly.state,
      employee: elderly.employee,
      userApp: elderly.userApp,
      families: elderly.families
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const elderly = this.createFromForm();
    if (elderly.id !== undefined) {
      this.subscribeToSaveResponse(this.elderlyService.update(elderly));
    } else {
      this.subscribeToSaveResponse(this.elderlyService.create(elderly));
    }
  }

  private createFromForm(): IElderly {
    return {
      ...new Elderly(),
      id: this.editForm.get(['id']).value,
      idElderly: this.editForm.get(['idElderly']).value,
      nationality: this.editForm.get(['nationality']).value,
      address: this.editForm.get(['address']).value,
      admissionDate: this.editForm.get(['admissionDate']).value,
      state: this.editForm.get(['state']).value,
      employee: this.editForm.get(['employee']).value,
      userApp: this.editForm.get(['userApp']).value,
      families: this.editForm.get(['families']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IElderly>>) {
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

  trackUserAppById(index: number, item: IUserApp) {
    return item.id;
  }

  trackFamilyById(index: number, item: IFamily) {
    return item.id;
  }

  trackDoctorById(index: number, item: IDoctor) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
