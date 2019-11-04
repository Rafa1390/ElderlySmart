import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPharmacy, Pharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from './pharmacy.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';
import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from 'app/entities/provider/provider.service';
import { IAsylum } from 'app/shared/model/asylum.model';
import { AsylumService } from 'app/entities/asylum/asylum.service';

@Component({
  selector: 'jhi-pharmacy-update',
  templateUrl: './pharmacy-update.component.html'
})
export class PharmacyUpdateComponent implements OnInit {
  isSaving: boolean;

  userapps: IUserApp[];

  providers: IProvider[];

  asylums: IAsylum[];

  editForm = this.fb.group({
    id: [],
    idPharmacy: [],
    email: [],
    name: [],
    address: [],
    userApp: [],
    providers: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pharmacyService: PharmacyService,
    protected userAppService: UserAppService,
    protected providerService: ProviderService,
    protected asylumService: AsylumService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pharmacy }) => {
      this.updateForm(pharmacy);
    });
    this.userAppService
      .query({ filter: 'pharmacy-is-null' })
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
    this.providerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProvider[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProvider[]>) => response.body)
      )
      .subscribe((res: IProvider[]) => (this.providers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.asylumService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAsylum[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAsylum[]>) => response.body)
      )
      .subscribe((res: IAsylum[]) => (this.asylums = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pharmacy: IPharmacy) {
    this.editForm.patchValue({
      id: pharmacy.id,
      idPharmacy: pharmacy.idPharmacy,
      email: pharmacy.email,
      name: pharmacy.name,
      address: pharmacy.address,
      userApp: pharmacy.userApp,
      providers: pharmacy.providers
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pharmacy = this.createFromForm();
    if (pharmacy.id !== undefined) {
      this.subscribeToSaveResponse(this.pharmacyService.update(pharmacy));
    } else {
      this.subscribeToSaveResponse(this.pharmacyService.create(pharmacy));
    }
  }

  private createFromForm(): IPharmacy {
    return {
      ...new Pharmacy(),
      id: this.editForm.get(['id']).value,
      idPharmacy: this.editForm.get(['idPharmacy']).value,
      email: this.editForm.get(['email']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value,
      userApp: this.editForm.get(['userApp']).value,
      providers: this.editForm.get(['providers']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPharmacy>>) {
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

  trackProviderById(index: number, item: IProvider) {
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
