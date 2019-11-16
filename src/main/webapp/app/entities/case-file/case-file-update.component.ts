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
import { ICaseFile, CaseFile } from 'app/shared/model/case-file.model';
import { CaseFileService } from './case-file.service';
import { IElderly } from 'app/shared/model/elderly.model';
import { ElderlyService } from 'app/entities/elderly/elderly.service';

@Component({
  selector: 'jhi-case-file-update',
  templateUrl: './case-file-update.component.html'
})
export class CaseFileUpdateComponent implements OnInit {
  isSaving: boolean;

  idelderlies: IElderly[];
  creationDateDp: any;
  birthDp: any;

  editForm = this.fb.group({
    id: [],
    idCaseFile: [],
    creationDate: [],
    fullNameElderly: [],
    age: [],
    religion: [],
    nationality: [],
    weight: [],
    height: [],
    birth: [],
    gender: [],
    bloodType: [],
    resuscitation: [],
    organDonation: [],
    state: [],
    idElderly: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected caseFileService: CaseFileService,
    protected elderlyService: ElderlyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ caseFile }) => {
      this.updateForm(caseFile);
    });
    this.elderlyService
      .query({ filter: 'casefile-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IElderly[]>) => mayBeOk.ok),
        map((response: HttpResponse<IElderly[]>) => response.body)
      )
      .subscribe(
        (res: IElderly[]) => {
          if (!this.editForm.get('idElderly').value || !this.editForm.get('idElderly').value.id) {
            this.idelderlies = res;
          } else {
            this.elderlyService
              .find(this.editForm.get('idElderly').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IElderly>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IElderly>) => subResponse.body)
              )
              .subscribe(
                (subRes: IElderly) => (this.idelderlies = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(caseFile: ICaseFile) {
    this.editForm.patchValue({
      id: caseFile.id,
      idCaseFile: caseFile.idCaseFile,
      creationDate: caseFile.creationDate,
      fullNameElderly: caseFile.fullNameElderly,
      age: caseFile.age,
      religion: caseFile.religion,
      nationality: caseFile.nationality,
      weight: caseFile.weight,
      height: caseFile.height,
      birth: caseFile.birth,
      gender: caseFile.gender,
      bloodType: caseFile.bloodType,
      resuscitation: caseFile.resuscitation,
      organDonation: caseFile.organDonation,
      state: caseFile.state,
      idElderly: caseFile.idElderly
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const caseFile = this.createFromForm();
    if (caseFile.id !== undefined) {
      this.subscribeToSaveResponse(this.caseFileService.update(caseFile));
    } else {
      this.subscribeToSaveResponse(this.caseFileService.create(caseFile));
    }
  }

  private createFromForm(): ICaseFile {
    return {
      ...new CaseFile(),
      id: this.editForm.get(['id']).value,
      idCaseFile: this.editForm.get(['idCaseFile']).value,
      creationDate: this.editForm.get(['creationDate']).value,
      fullNameElderly: this.editForm.get(['fullNameElderly']).value,
      age: this.editForm.get(['age']).value,
      religion: this.editForm.get(['religion']).value,
      nationality: this.editForm.get(['nationality']).value,
      weight: this.editForm.get(['weight']).value,
      height: this.editForm.get(['height']).value,
      birth: this.editForm.get(['birth']).value,
      gender: this.editForm.get(['gender']).value,
      bloodType: this.editForm.get(['bloodType']).value,
      resuscitation: this.editForm.get(['resuscitation']).value,
      organDonation: this.editForm.get(['organDonation']).value,
      state: this.editForm.get(['state']).value,
      idElderly: this.editForm.get(['idElderly']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaseFile>>) {
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

  trackElderlyById(index: number, item: IElderly) {
    return item.id;
  }
}
