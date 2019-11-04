import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicament } from 'app/shared/model/medicament.model';

type EntityResponseType = HttpResponse<IMedicament>;
type EntityArrayResponseType = HttpResponse<IMedicament[]>;

@Injectable({ providedIn: 'root' })
export class MedicamentService {
  public resourceUrl = SERVER_API_URL + 'api/medicaments';

  constructor(protected http: HttpClient) {}

  create(medicament: IMedicament): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicament);
    return this.http
      .post<IMedicament>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicament: IMedicament): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicament);
    return this.http
      .put<IMedicament>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicament>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicament[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medicament: IMedicament): IMedicament {
    const copy: IMedicament = Object.assign({}, medicament, {
      dateExpiry: medicament.dateExpiry != null && medicament.dateExpiry.isValid() ? medicament.dateExpiry.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateExpiry = res.body.dateExpiry != null ? moment(res.body.dateExpiry) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicament: IMedicament) => {
        medicament.dateExpiry = medicament.dateExpiry != null ? moment(medicament.dateExpiry) : null;
      });
    }
    return res;
  }
}
