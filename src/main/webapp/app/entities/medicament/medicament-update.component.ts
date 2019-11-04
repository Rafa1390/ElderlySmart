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
import { IMedicament, Medicament } from 'app/shared/model/medicament.model';
import { MedicamentService } from './medicament.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-medicament-update',
  templateUrl: './medicament-update.component.html'
})
export class MedicamentUpdateComponent implements OnInit {
  isSaving: boolean;

  products: IProduct[];
  dateExpiryDp: any;

  editForm = this.fb.group({
    id: [],
    idMedicament: [],
    presentation: [],
    dateExpiry: [],
    cuantity: [],
    product: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected medicamentService: MedicamentService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicament }) => {
      this.updateForm(medicament);
    });
    this.productService
      .query({ filter: 'medicament-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe(
        (res: IProduct[]) => {
          if (!this.editForm.get('product').value || !this.editForm.get('product').value.id) {
            this.products = res;
          } else {
            this.productService
              .find(this.editForm.get('product').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProduct>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProduct>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProduct) => (this.products = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(medicament: IMedicament) {
    this.editForm.patchValue({
      id: medicament.id,
      idMedicament: medicament.idMedicament,
      presentation: medicament.presentation,
      dateExpiry: medicament.dateExpiry,
      cuantity: medicament.cuantity,
      product: medicament.product
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicament = this.createFromForm();
    if (medicament.id !== undefined) {
      this.subscribeToSaveResponse(this.medicamentService.update(medicament));
    } else {
      this.subscribeToSaveResponse(this.medicamentService.create(medicament));
    }
  }

  private createFromForm(): IMedicament {
    return {
      ...new Medicament(),
      id: this.editForm.get(['id']).value,
      idMedicament: this.editForm.get(['idMedicament']).value,
      presentation: this.editForm.get(['presentation']).value,
      dateExpiry: this.editForm.get(['dateExpiry']).value,
      cuantity: this.editForm.get(['cuantity']).value,
      product: this.editForm.get(['product']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicament>>) {
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

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }
}
