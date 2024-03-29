import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';

type EntityResponseType = HttpResponse<IMedicalAppointment>;
type EntityArrayResponseType = HttpResponse<IMedicalAppointment[]>;

@Injectable({ providedIn: 'root' })
export class MedicalAppointmentService {
  public resourceUrl = SERVER_API_URL + 'api/medical-appointments';

  constructor(protected http: HttpClient) {}

  create(medicalAppointment: IMedicalAppointment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalAppointment);
    return this.http
      .post<IMedicalAppointment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalAppointment: IMedicalAppointment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalAppointment);
    return this.http
      .put<IMedicalAppointment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalAppointment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalAppointment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medicalAppointment: IMedicalAppointment): IMedicalAppointment {
    const copy: IMedicalAppointment = Object.assign({}, medicalAppointment, {
      date: medicalAppointment.date != null && medicalAppointment.date.isValid() ? medicalAppointment.date.format(DATE_FORMAT) : null
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
      res.body.forEach((medicalAppointment: IMedicalAppointment) => {
        medicalAppointment.date = medicalAppointment.date != null ? moment(medicalAppointment.date) : null;
      });
    }
    return res;
  }
}
