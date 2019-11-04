import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPharmacyHistory, PharmacyHistory } from 'app/shared/model/pharmacy-history.model';
import { PharmacyHistoryService } from './pharmacy-history.service';

@Component({
  selector: 'jhi-pharmacy-history-update',
  templateUrl: './pharmacy-history-update.component.html'
})
export class PharmacyHistoryUpdateComponent implements OnInit {
  isSaving: boolean;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    idPharmacyHistory: [],
    code: [],
    client: [],
    phone: [],
    purchaseMade: [],
    date: []
  });

  constructor(
    protected pharmacyHistoryService: PharmacyHistoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pharmacyHistory }) => {
      this.updateForm(pharmacyHistory);
    });
  }

  updateForm(pharmacyHistory: IPharmacyHistory) {
    this.editForm.patchValue({
      id: pharmacyHistory.id,
      idPharmacyHistory: pharmacyHistory.idPharmacyHistory,
      code: pharmacyHistory.code,
      client: pharmacyHistory.client,
      phone: pharmacyHistory.phone,
      purchaseMade: pharmacyHistory.purchaseMade,
      date: pharmacyHistory.date
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pharmacyHistory = this.createFromForm();
    if (pharmacyHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.pharmacyHistoryService.update(pharmacyHistory));
    } else {
      this.subscribeToSaveResponse(this.pharmacyHistoryService.create(pharmacyHistory));
    }
  }

  private createFromForm(): IPharmacyHistory {
    return {
      ...new PharmacyHistory(),
      id: this.editForm.get(['id']).value,
      idPharmacyHistory: this.editForm.get(['idPharmacyHistory']).value,
      code: this.editForm.get(['code']).value,
      client: this.editForm.get(['client']).value,
      phone: this.editForm.get(['phone']).value,
      purchaseMade: this.editForm.get(['purchaseMade']).value,
      date: this.editForm.get(['date']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPharmacyHistory>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
