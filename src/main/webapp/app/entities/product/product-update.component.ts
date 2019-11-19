import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IInventoryProvider } from 'app/shared/model/inventory-provider.model';
import { InventoryProviderService } from 'app/entities/inventory-provider/inventory-provider.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving: boolean;

  inventoryproviders: IInventoryProvider[];

  editForm = this.fb.group({
    id: [],
    idProduct: [],
    code: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    brand: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    type: new FormControl('', [Validators.required]),
    state: new FormControl('', [Validators.required])
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productService: ProductService,
    protected inventoryProviderService: InventoryProviderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
    });
    this.inventoryProviderService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInventoryProvider[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInventoryProvider[]>) => response.body)
      )
      .subscribe((res: IInventoryProvider[]) => (this.inventoryproviders = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(product: IProduct) {
    this.editForm.patchValue({
      id: product.id,
      idProduct: product.idProduct,
      code: product.code,
      name: product.name,
      brand: product.brand,
      description: product.description,
      type: product.type,
      state: product.state
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id']).value,
      idProduct: this.editForm.get(['idProduct']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      brand: this.editForm.get(['brand']).value,
      description: this.editForm.get(['description']).value,
      type: this.editForm.get(['type']).value,
      state: this.editForm.get(['state']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
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

  trackInventoryProviderById(index: number, item: IInventoryProvider) {
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

  get idProduct() {
    return this.editForm.get('idProduct');
  }

  get code() {
    return this.editForm.get('code');
  }

  get name() {
    return this.editForm.get('name');
  }

  get brand() {
    return this.editForm.get('brand');
  }

  get description() {
    return this.editForm.get('description');
  }

  get type() {
    return this.editForm.get('type');
  }
  get state() {
    return this.editForm.get('state');
  }
}
