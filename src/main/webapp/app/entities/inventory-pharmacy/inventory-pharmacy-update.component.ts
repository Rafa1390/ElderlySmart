import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInventoryPharmacy, InventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';
import { InventoryPharmacyService } from './inventory-pharmacy.service';
import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';

@Component({
  selector: 'jhi-inventory-pharmacy-update',
  templateUrl: './inventory-pharmacy-update.component.html'
})
export class InventoryPharmacyUpdateComponent implements OnInit {
  isSaving: boolean;

  pharmacies: IPharmacy[];

  editForm = this.fb.group({
    id: [],
    idInventoryPharmacy: [],
    code: [],
    name: [],
    purchasePrice: [],
    salePrice: [],
    cuantity: [],
    pharmacy: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected inventoryPharmacyService: InventoryPharmacyService,
    protected pharmacyService: PharmacyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventoryPharmacy }) => {
      this.updateForm(inventoryPharmacy);
    });
    this.pharmacyService
      .query({ filter: 'inventorypharmacy-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPharmacy[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPharmacy[]>) => response.body)
      )
      .subscribe(
        (res: IPharmacy[]) => {
          if (!this.editForm.get('pharmacy').value || !this.editForm.get('pharmacy').value.id) {
            this.pharmacies = res;
          } else {
            this.pharmacyService
              .find(this.editForm.get('pharmacy').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPharmacy>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPharmacy>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPharmacy) => (this.pharmacies = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(inventoryPharmacy: IInventoryPharmacy) {
    this.editForm.patchValue({
      id: inventoryPharmacy.id,
      idInventoryPharmacy: inventoryPharmacy.idInventoryPharmacy,
      code: inventoryPharmacy.code,
      name: inventoryPharmacy.name,
      purchasePrice: inventoryPharmacy.purchasePrice,
      salePrice: inventoryPharmacy.salePrice,
      cuantity: inventoryPharmacy.cuantity,
      pharmacy: inventoryPharmacy.pharmacy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventoryPharmacy = this.createFromForm();
    if (inventoryPharmacy.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryPharmacyService.update(inventoryPharmacy));
    } else {
      this.subscribeToSaveResponse(this.inventoryPharmacyService.create(inventoryPharmacy));
    }
  }

  private createFromForm(): IInventoryPharmacy {
    return {
      ...new InventoryPharmacy(),
      id: this.editForm.get(['id']).value,
      idInventoryPharmacy: this.editForm.get(['idInventoryPharmacy']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      purchasePrice: this.editForm.get(['purchasePrice']).value,
      salePrice: this.editForm.get(['salePrice']).value,
      cuantity: this.editForm.get(['cuantity']).value,
      pharmacy: this.editForm.get(['pharmacy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryPharmacy>>) {
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
}
