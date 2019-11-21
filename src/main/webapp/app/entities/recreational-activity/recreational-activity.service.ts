import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRecreationalActivity, RecreationalActivity } from 'app/shared/model/recreational-activity.model';

type EntityResponseType = HttpResponse<IRecreationalActivity>;
type EntityArrayResponseType = HttpResponse<IRecreationalActivity[]>;

@Injectable({ providedIn: 'root' })
export class RecreationalActivityService {
  public resourceUrl = SERVER_API_URL + 'api/recreational-activities';

  constructor(protected http: HttpClient) {}

  create(recreationalActivity: IRecreationalActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recreationalActivity);
    return this.http
      .post<IRecreationalActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(recreationalActivity: IRecreationalActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recreationalActivity);
    return this.http
      .put<IRecreationalActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRecreationalActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRecreationalActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  getData() {
    return this.http.get<RecreationalActivity[]>(this.resourceUrl);
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(recreationalActivity: IRecreationalActivity): IRecreationalActivity {
    const copy: IRecreationalActivity = Object.assign({}, recreationalActivity, {
      date: recreationalActivity.date != null && recreationalActivity.date.isValid() ? recreationalActivity.date.format(DATE_FORMAT) : null
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
      res.body.forEach((recreationalActivity: IRecreationalActivity) => {
        recreationalActivity.date = recreationalActivity.date != null ? moment(recreationalActivity.date) : null;
      });
    }
    return res;
  }
}
