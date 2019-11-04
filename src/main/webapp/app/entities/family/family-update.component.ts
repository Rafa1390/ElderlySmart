import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFamily, Family } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';
import { IElderly } from 'app/shared/model/elderly.model';
import { ElderlyService } from 'app/entities/elderly/elderly.service';

@Component({
  selector: 'jhi-family-update',
  templateUrl: './family-update.component.html'
})
export class FamilyUpdateComponent implements OnInit {
  isSaving: boolean;

  userapps: IUserApp[];

  elderlies: IElderly[];

  editForm = this.fb.group({
    id: [],
    idFamily: [],
    familyRelation: [],
    userApp: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected familyService: FamilyService,
    protected userAppService: UserAppService,
    protected elderlyService: ElderlyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ family }) => {
      this.updateForm(family);
    });
    this.userAppService
      .query({ filter: 'family-is-null' })
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
  }

  updateForm(family: IFamily) {
    this.editForm.patchValue({
      id: family.id,
      idFamily: family.idFamily,
      familyRelation: family.familyRelation,
      userApp: family.userApp
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const family = this.createFromForm();
    if (family.id !== undefined) {
      this.subscribeToSaveResponse(this.familyService.update(family));
    } else {
      this.subscribeToSaveResponse(this.familyService.create(family));
    }
  }

  private createFromForm(): IFamily {
    return {
      ...new Family(),
      id: this.editForm.get(['id']).value,
      idFamily: this.editForm.get(['idFamily']).value,
      familyRelation: this.editForm.get(['familyRelation']).value,
      userApp: this.editForm.get(['userApp']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamily>>) {
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
