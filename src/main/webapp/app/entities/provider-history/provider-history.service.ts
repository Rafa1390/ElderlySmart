import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProviderHistory } from 'app/shared/model/provider-history.model';

type EntityResponseType = HttpResponse<IProviderHistory>;
type EntityArrayResponseType = HttpResponse<IProviderHistory[]>;

@Injectable({ providedIn: 'root' })
export class ProviderHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/provider-histories';

  constructor(protected http: HttpClient) {}

  create(providerHistory: IProviderHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(providerHistory);
    return this.http
      .post<IProviderHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(providerHistory: IProviderHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(providerHistory);
    return this.http
      .put<IProviderHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProviderHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProviderHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(providerHistory: IProviderHistory): IProviderHistory {
    const copy: IProviderHistory = Object.assign({}, providerHistory, {
      date: providerHistory.date != null && providerHistory.date.isValid() ? providerHistory.date.format(DATE_FORMAT) : null
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
      res.body.forEach((providerHistory: IProviderHistory) => {
        providerHistory.date = providerHistory.date != null ? moment(providerHistory.date) : null;
      });
    }
    return res;
  }
}
