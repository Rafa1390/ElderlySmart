import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDoctor, Doctor } from 'app/shared/model/doctor.model';
import { DoctorService } from './doctor.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';
import { IElderly } from 'app/shared/model/elderly.model';
import { ElderlyService } from 'app/entities/elderly/elderly.service';

@Component({
  selector: 'jhi-doctor-update',
  templateUrl: './doctor-update.component.html'
})
export class DoctorUpdateComponent implements OnInit {
  isSaving: boolean;

  userapps: IUserApp[];

  elderlies: IElderly[];

  labelCreateDoctor = 'Registro | Doctor';

  editForm = this.fb.group({
    id: [],
    idDoctor: [],
    email: [],
    officeName: [],
    address: [],
    userApp: [],
    elderlies: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected doctorService: DoctorService,
    protected userAppService: UserAppService,
    protected elderlyService: ElderlyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ doctor }) => {
      this.updateForm(doctor);
    });
    this.userAppService
      .query({ filter: 'doctor-is-null' })
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
    this.elderlyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IElderly[]>) => mayBeOk.ok),
        map((response: HttpResponse<IElderly[]>) => response.body)
      )
      .subscribe((res: IElderly[]) => (this.elderlies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.proveLabel();
  }

  proveLabel() {
    const doctor = this.createFromForm();
    if (doctor.id !== undefined) {
      this.labelCreateDoctor = 'Editar | Doctor';
    }
  }

  updateForm(doctor: IDoctor) {
    this.editForm.patchValue({
      id: doctor.id,
      idDoctor: doctor.idDoctor,
      email: doctor.email,
      officeName: doctor.officeName,
      address: doctor.address,
      userApp: doctor.userApp,
      elderlies: doctor.elderlies
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const doctor = this.createFromForm();
    if (doctor.id !== undefined) {
      this.subscribeToSaveResponse(this.doctorService.update(doctor));
    } else {
      this.subscribeToSaveResponse(this.doctorService.create(doctor));
    }
  }

  private createFromForm(): IDoctor {
    return {
      ...new Doctor(),
      id: this.editForm.get(['id']).value,
      idDoctor: this.editForm.get(['idDoctor']).value,
      email: this.editForm.get(['email']).value,
      officeName: this.editForm.get(['officeName']).value,
      address: this.editForm.get(['address']).value,
      userApp: this.editForm.get(['userApp']).value,
      elderlies: this.editForm.get(['elderlies']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDoctor>>) {
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

  trackUserAppById(index: number, item: IUserApp) {
    return item.id;
  }

  trackElderlyById(index: number, item: IElderly) {
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
