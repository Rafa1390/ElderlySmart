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
import { IProviderHistory, ProviderHistory } from 'app/shared/model/provider-history.model';
import { ProviderHistoryService } from './provider-history.service';
import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from 'app/entities/provider/provider.service';

@Component({
  selector: 'jhi-provider-history-update',
  templateUrl: './provider-history-update.component.html'
})
export class ProviderHistoryUpdateComponent implements OnInit {
  isSaving: boolean;

  providers: IProvider[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    idProviderHistory: [],
    code: [],
    client: [],
    phone: [],
    purchaseMade: [],
    date: [],
    provider: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected providerHistoryService: ProviderHistoryService,
    protected providerService: ProviderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ providerHistory }) => {
      this.updateForm(providerHistory);
    });
    this.providerService
      .query({ filter: 'providerhistory-is-null' })
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
  }

  updateForm(providerHistory: IProviderHistory) {
    this.editForm.patchValue({
      id: providerHistory.id,
      idProviderHistory: providerHistory.idProviderHistory,
      code: providerHistory.code,
      client: providerHistory.client,
      phone: providerHistory.phone,
      purchaseMade: providerHistory.purchaseMade,
      date: providerHistory.date,
      provider: providerHistory.provider
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const providerHistory = this.createFromForm();
    if (providerHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.providerHistoryService.update(providerHistory));
    } else {
      this.subscribeToSaveResponse(this.providerHistoryService.create(providerHistory));
    }
  }

  private createFromForm(): IProviderHistory {
    return {
      ...new ProviderHistory(),
      id: this.editForm.get(['id']).value,
      idProviderHistory: this.editForm.get(['idProviderHistory']).value,
      code: this.editForm.get(['code']).value,
      client: this.editForm.get(['client']).value,
      phone: this.editForm.get(['phone']).value,
      purchaseMade: this.editForm.get(['purchaseMade']).value,
      date: this.editForm.get(['date']).value,
      provider: this.editForm.get(['provider']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProviderHistory>>) {
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
}
