import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAsylum, Asylum } from 'app/shared/model/asylum.model';
import { AsylumService } from './asylum.service';
import { IUserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from 'app/entities/user-app/user-app.service';
import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';
import { IMortuary } from 'app/shared/model/mortuary.model';
import { MortuaryService } from 'app/entities/mortuary/mortuary.service';
import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from 'app/entities/partner/partner.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-asylum-update',
  templateUrl: './asylum-update.component.html'
})
export class AsylumUpdateComponent implements OnInit {
  isSaving: boolean;

  userapps: IUserApp[];

  pharmacies: IPharmacy[];

  mortuaries: IMortuary[];

  partners: IPartner[];

  editForm = this.fb.group({
    id: [],
    identification: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required]),
    userApp: new FormControl('', [Validators.required]),
    pharmacies: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected asylumService: AsylumService,
    protected userAppService: UserAppService,
    protected pharmacyService: PharmacyService,
    protected mortuaryService: MortuaryService,
    protected partnerService: PartnerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ asylum }) => {
      this.updateForm(asylum);
    });
    this.userAppService
      .query({ filter: 'asylum-is-null' })
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
    this.mortuaryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMortuary[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMortuary[]>) => response.body)
      )
      .subscribe((res: IMortuary[]) => (this.mortuaries = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.partnerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPartner[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPartner[]>) => response.body)
      )
      .subscribe((res: IPartner[]) => (this.partners = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(asylum: IAsylum) {
    this.editForm.patchValue({
      id: asylum.id,
      identification: asylum.identification,
      email: asylum.email,
      name: asylum.name,
      address: asylum.address,
      userApp: asylum.userApp,
      pharmacies: asylum.pharmacies
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const asylum = this.createFromForm();
    if (asylum.id !== undefined) {
      this.subscribeToSaveResponse(this.asylumService.update(asylum));
    } else {
      this.subscribeToSaveResponse(this.asylumService.create(asylum));
    }
  }

  private createFromForm(): IAsylum {
    return {
      ...new Asylum(),
      id: this.editForm.get(['id']).value,
      identification: this.editForm.get(['identification']).value,
      email: this.editForm.get(['email']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value,
      userApp: this.editForm.get(['userApp']).value,
      pharmacies: this.editForm.get(['pharmacies']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsylum>>) {
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

  trackMortuaryById(index: number, item: IMortuary) {
    return item.id;
  }

  trackPartnerById(index: number, item: IPartner) {
    return item.id;
  }

  get identification() {
    return this.editForm.get('identification');
  }

  get name() {
    return this.editForm.get('name');
  }

  get email() {
    return this.editForm.get('email');
  }

  get address() {
    return this.editForm.get('address');
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
