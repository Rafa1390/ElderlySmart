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
import { IDonationHistory, DonationHistory } from 'app/shared/model/donation-history.model';
import { DonationHistoryService } from './donation-history.service';
import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from 'app/entities/partner/partner.service';

@Component({
  selector: 'jhi-donation-history-update',
  templateUrl: './donation-history-update.component.html'
})
export class DonationHistoryUpdateComponent implements OnInit {
  isSaving: boolean;

  partners: IPartner[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    idDonationHistory: [],
    phone: [],
    donationMade: [],
    date: [],
    partner: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected donationHistoryService: DonationHistoryService,
    protected partnerService: PartnerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ donationHistory }) => {
      this.updateForm(donationHistory);
    });
    this.partnerService
      .query({ filter: 'donationhistory-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPartner[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPartner[]>) => response.body)
      )
      .subscribe(
        (res: IPartner[]) => {
          if (!this.editForm.get('partner').value || !this.editForm.get('partner').value.id) {
            this.partners = res;
          } else {
            this.partnerService
              .find(this.editForm.get('partner').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPartner>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPartner>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPartner) => (this.partners = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(donationHistory: IDonationHistory) {
    this.editForm.patchValue({
      id: donationHistory.id,
      idDonationHistory: donationHistory.idDonationHistory,
      phone: donationHistory.phone,
      donationMade: donationHistory.donationMade,
      date: donationHistory.date,
      partner: donationHistory.partner
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const donationHistory = this.createFromForm();
    if (donationHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.donationHistoryService.update(donationHistory));
    } else {
      this.subscribeToSaveResponse(this.donationHistoryService.create(donationHistory));
    }
  }

  private createFromForm(): IDonationHistory {
    return {
      ...new DonationHistory(),
      id: this.editForm.get(['id']).value,
      idDonationHistory: this.editForm.get(['idDonationHistory']).value,
      phone: this.editForm.get(['phone']).value,
      donationMade: this.editForm.get(['donationMade']).value,
      date: this.editForm.get(['date']).value,
      partner: this.editForm.get(['partner']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonationHistory>>) {
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

  trackPartnerById(index: number, item: IPartner) {
    return item.id;
  }
}
