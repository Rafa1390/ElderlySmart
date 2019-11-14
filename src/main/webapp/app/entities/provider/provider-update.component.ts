import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProvider, Provider } from 'app/shared/model/provider.model';
import { ProviderService } from './provider.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';
import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';

@Component({
  selector: 'jhi-provider-update',
  templateUrl: './provider-update.component.html'
})
export class ProviderUpdateComponent implements OnInit {
  isSaving: boolean;
  userapps: IUserApp[];

  pharmacies: IPharmacy[];

  editForm = this.fb.group({
    id: [],
    identification: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    providerType: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required]),
    userApp: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected providerService: ProviderService,
    protected userAppService: UserAppService,
    protected pharmacyService: PharmacyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ provider }) => {
      this.updateForm(provider);
    });
    this.userAppService
      .query({ filter: 'provider-is-null' })
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
    this.pharmacyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPharmacy[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPharmacy[]>) => response.body)
      )
      .subscribe((res: IPharmacy[]) => (this.pharmacies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(provider: IProvider) {
    this.editForm.patchValue({
      id: provider.id,
      identification: provider.identification,
      email: provider.email,
      name: provider.name,
      providerType: provider.providerType,
      address: provider.address,
      userApp: provider.userApp
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const provider = this.createFromForm();
    if (provider.id !== undefined) {
      this.subscribeToSaveResponse(this.providerService.update(provider));
    } else {
      this.subscribeToSaveResponse(this.providerService.create(provider));
    }
  }

  private createFromForm(): IProvider {
    return {
      ...new Provider(),
      id: this.editForm.get(['id']).value,
      identification: this.editForm.get(['identification']).value,
      email: this.editForm.get(['email']).value,
      name: this.editForm.get(['name']).value,
      providerType: this.editForm.get(['providerType']).value,
      address: this.editForm.get(['address']).value,
      userApp: this.editForm.get(['userApp']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvider>>) {
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

  trackPharmacyById(index: number, item: IPharmacy) {
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

  get identification() {
    return this.editForm.get('identification');
  }

  get email() {
    return this.editForm.get('email');
  }

  get name() {
    return this.editForm.get('name');
  }

  get providerType() {
    return this.editForm.get('providerType');
  }

  get address() {
    return this.editForm.get('address');
  }
}
