import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IElderly } from 'app/shared/model/elderly.model';

type EntityResponseType = HttpResponse<IElderly>;
type EntityArrayResponseType = HttpResponse<IElderly[]>;

@Injectable({ providedIn: 'root' })
export class ElderlyService {
  public resourceUrl = SERVER_API_URL + 'api/elderlies';

  constructor(protected http: HttpClient) {}

  create(elderly: IElderly): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(elderly);
    return this.http
      .post<IElderly>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(elderly: IElderly): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(elderly);
    return this.http
      .put<IElderly>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IElderly>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IElderly[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(elderly: IElderly): IElderly {
    const copy: IElderly = Object.assign({}, elderly, {
      admissionDate: elderly.admissionDate != null && elderly.admissionDate.isValid() ? elderly.admissionDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.admissionDate = res.body.admissionDate != null ? moment(res.body.admissionDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((elderly: IElderly) => {
        elderly.admissionDate = elderly.admissionDate != null ? moment(elderly.admissionDate) : null;
      });
    }
    return res;
  }
}
