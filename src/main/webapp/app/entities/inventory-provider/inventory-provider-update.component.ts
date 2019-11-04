import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInventoryProvider, InventoryProvider } from 'app/shared/model/inventory-provider.model';
import { InventoryProviderService } from './inventory-provider.service';
import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from 'app/entities/provider/provider.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-inventory-provider-update',
  templateUrl: './inventory-provider-update.component.html'
})
export class InventoryProviderUpdateComponent implements OnInit {
  isSaving: boolean;

  providers: IProvider[];

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    idInventoryProvider: [],
    code: [],
    name: [],
    price: [],
    cuantity: [],
    provider: [],
    products: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected inventoryProviderService: InventoryProviderService,
    protected providerService: ProviderService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventoryProvider }) => {
      this.updateForm(inventoryProvider);
    });
    this.providerService
      .query({ filter: 'inventoryprovider-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProvider[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProvider[]>) => response.body)
      )
      .subscribe(
        (res: IProvider[]) => {
          if (!this.editForm.get('provider').value || !this.editForm.get('provider').value.id) {
            this.providers = res;
          } else {
            this.providerService
              .find(this.editForm.get('provider').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProvider>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProvider>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProvider) => (this.providers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(inventoryProvider: IInventoryProvider) {
    this.editForm.patchValue({
      id: inventoryProvider.id,
      idInventoryProvider: inventoryProvider.idInventoryProvider,
      code: inventoryProvider.code,
      name: inventoryProvider.name,
      price: inventoryProvider.price,
      cuantity: inventoryProvider.cuantity,
      provider: inventoryProvider.provider,
      products: inventoryProvider.products
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventoryProvider = this.createFromForm();
    if (inventoryProvider.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryProviderService.update(inventoryProvider));
    } else {
      this.subscribeToSaveResponse(this.inventoryProviderService.create(inventoryProvider));
    }
  }

  private createFromForm(): IInventoryProvider {
    return {
      ...new InventoryProvider(),
      id: this.editForm.get(['id']).value,
      idInventoryProvider: this.editForm.get(['idInventoryProvider']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      price: this.editForm.get(['price']).value,
      cuantity: this.editForm.get(['cuantity']).value,
      provider: this.editForm.get(['provider']).value,
      products: this.editForm.get(['products']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryProvider>>) {
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

  trackProviderById(index: number, item: IProvider) {
    return item.id;
  }

  trackProductById(index: number, item: IProduct) {
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
