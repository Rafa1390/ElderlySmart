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
import { IFuneralHistory, FuneralHistory } from 'app/shared/model/funeral-history.model';
import { FuneralHistoryService } from './funeral-history.service';
import { IMortuary } from 'app/shared/model/mortuary.model';
import { MortuaryService } from 'app/entities/mortuary/mortuary.service';

@Component({
  selector: 'jhi-funeral-history-update',
  templateUrl: './funeral-history-update.component.html'
})
export class FuneralHistoryUpdateComponent implements OnInit {
  isSaving: boolean;

  mortuaries: IMortuary[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    idFuneralHistory: [],
    customer: [],
    phone: [],
    purchaseMade: [],
    date: [],
    state: [],
    mortuary: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected funeralHistoryService: FuneralHistoryService,
    protected mortuaryService: MortuaryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ funeralHistory }) => {
      this.updateForm(funeralHistory);
    });
    this.mortuaryService
      .query({ filter: 'funeralhistory-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IMortuary[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMortuary[]>) => response.body)
      )
      .subscribe(
        (res: IMortuary[]) => {
          if (!this.editForm.get('mortuary').value || !this.editForm.get('mortuary').value.id) {
            this.mortuaries = res;
          } else {
            this.mortuaryService
              .find(this.editForm.get('mortuary').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IMortuary>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IMortuary>) => subResponse.body)
              )
              .subscribe(
                (subRes: IMortuary) => (this.mortuaries = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(funeralHistory: IFuneralHistory) {
    this.editForm.patchValue({
      id: funeralHistory.id,
      idFuneralHistory: funeralHistory.idFuneralHistory,
      customer: funeralHistory.customer,
      phone: funeralHistory.phone,
      purchaseMade: funeralHistory.purchaseMade,
      date: funeralHistory.date,
      state: funeralHistory.state,
      mortuary: funeralHistory.mortuary
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const funeralHistory = this.createFromForm();
    if (funeralHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.funeralHistoryService.update(funeralHistory));
    } else {
      this.subscribeToSaveResponse(this.funeralHistoryService.create(funeralHistory));
    }
  }

  private createFromForm(): IFuneralHistory {
    return {
      ...new FuneralHistory(),
      id: this.editForm.get(['id']).value,
      idFuneralHistory: this.editForm.get(['idFuneralHistory']).value,
      customer: this.editForm.get(['customer']).value,
      phone: this.editForm.get(['phone']).value,
      purchaseMade: this.editForm.get(['purchaseMade']).value,
      date: this.editForm.get(['date']).value,
      state: this.editForm.get(['state']).value,
      mortuary: this.editForm.get(['mortuary']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuneralHistory>>) {
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

  trackMortuaryById(index: number, item: IMortuary) {
    return item.id;
  }
}
