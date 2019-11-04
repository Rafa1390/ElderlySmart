import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMortuary, Mortuary } from 'app/shared/model/mortuary.model';
import { MortuaryService } from './mortuary.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';
import { IAsylum } from 'app/shared/model/asylum.model';
import { AsylumService } from 'app/entities/asylum/asylum.service';

@Component({
  selector: 'jhi-mortuary-update',
  templateUrl: './mortuary-update.component.html'
})
export class MortuaryUpdateComponent implements OnInit {
  isSaving: boolean;

  userapps: IUserApp[];

  asylums: IAsylum[];

  editForm = this.fb.group({
    id: [],
    idMortuary: [],
    email: [],
    name: [],
    address: [],
    userApp: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected mortuaryService: MortuaryService,
    protected userAppService: UserAppService,
    protected asylumService: AsylumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ mortuary }) => {
      this.updateForm(mortuary);
    });
    this.userAppService
      .query({ filter: 'mortuary-is-null' })
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
    this.asylumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAsylum[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAsylum[]>) => response.body)
      )
      .subscribe((res: IAsylum[]) => (this.asylums = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(mortuary: IMortuary) {
    this.editForm.patchValue({
      id: mortuary.id,
      idMortuary: mortuary.idMortuary,
      email: mortuary.email,
      name: mortuary.name,
      address: mortuary.address,
      userApp: mortuary.userApp
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const mortuary = this.createFromForm();
    if (mortuary.id !== undefined) {
      this.subscribeToSaveResponse(this.mortuaryService.update(mortuary));
    } else {
      this.subscribeToSaveResponse(this.mortuaryService.create(mortuary));
    }
  }

  private createFromForm(): IMortuary {
    return {
      ...new Mortuary(),
      id: this.editForm.get(['id']).value,
      idMortuary: this.editForm.get(['idMortuary']).value,
      email: this.editForm.get(['email']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value,
      userApp: this.editForm.get(['userApp']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMortuary>>) {
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

  trackAsylumById(index: number, item: IAsylum) {
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
