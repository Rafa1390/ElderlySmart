import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFuneralHistory } from 'app/shared/model/funeral-history.model';

type EntityResponseType = HttpResponse<IFuneralHistory>;
type EntityArrayResponseType = HttpResponse<IFuneralHistory[]>;

@Injectable({ providedIn: 'root' })
export class FuneralHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/funeral-histories';

  constructor(protected http: HttpClient) {}

  create(funeralHistory: IFuneralHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funeralHistory);
    return this.http
      .post<IFuneralHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(funeralHistory: IFuneralHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funeralHistory);
    return this.http
      .put<IFuneralHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFuneralHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFuneralHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(funeralHistory: IFuneralHistory): IFuneralHistory {
    const copy: IFuneralHistory = Object.assign({}, funeralHistory, {
      date: funeralHistory.date != null && funeralHistory.date.isValid() ? funeralHistory.date.format(DATE_FORMAT) : null
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
      res.body.forEach((funeralHistory: IFuneralHistory) => {
        funeralHistory.date = funeralHistory.date != null ? moment(funeralHistory.date) : null;
      });
    }
    return res;
  }
}
