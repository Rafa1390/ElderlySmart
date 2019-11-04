import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDonationHistory } from 'app/shared/model/donation-history.model';

type EntityResponseType = HttpResponse<IDonationHistory>;
type EntityArrayResponseType = HttpResponse<IDonationHistory[]>;

@Injectable({ providedIn: 'root' })
export class DonationHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/donation-histories';

  constructor(protected http: HttpClient) {}

  create(donationHistory: IDonationHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationHistory);
    return this.http
      .post<IDonationHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(donationHistory: IDonationHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(donationHistory);
    return this.http
      .put<IDonationHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDonationHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDonationHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(donationHistory: IDonationHistory): IDonationHistory {
    const copy: IDonationHistory = Object.assign({}, donationHistory, {
      date: donationHistory.date != null && donationHistory.date.isValid() ? donationHistory.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((donationHistory: IDonationHistory) => {
        donationHistory.date = donationHistory.date != null ? moment(donationHistory.date) : null;
      });
    }
    return res;
  }
}
