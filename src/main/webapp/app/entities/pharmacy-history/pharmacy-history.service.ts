import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPharmacyHistory } from 'app/shared/model/pharmacy-history.model';

type EntityResponseType = HttpResponse<IPharmacyHistory>;
type EntityArrayResponseType = HttpResponse<IPharmacyHistory[]>;

@Injectable({ providedIn: 'root' })
export class PharmacyHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/pharmacy-histories';

  constructor(protected http: HttpClient) {}

  create(pharmacyHistory: IPharmacyHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pharmacyHistory);
    return this.http
      .post<IPharmacyHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pharmacyHistory: IPharmacyHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pharmacyHistory);
    return this.http
      .put<IPharmacyHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPharmacyHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPharmacyHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pharmacyHistory: IPharmacyHistory): IPharmacyHistory {
    const copy: IPharmacyHistory = Object.assign({}, pharmacyHistory, {
      date: pharmacyHistory.date != null && pharmacyHistory.date.isValid() ? pharmacyHistory.date.format(DATE_FORMAT) : null
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
      res.body.forEach((pharmacyHistory: IPharmacyHistory) => {
        pharmacyHistory.date = pharmacyHistory.date != null ? moment(pharmacyHistory.date) : null;
      });
    }
    return res;
  }
}
