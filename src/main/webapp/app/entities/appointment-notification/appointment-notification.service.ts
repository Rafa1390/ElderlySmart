import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppointmentNotification } from 'app/shared/model/appointment-notification.model';

type EntityResponseType = HttpResponse<IAppointmentNotification>;
type EntityArrayResponseType = HttpResponse<IAppointmentNotification[]>;

@Injectable({ providedIn: 'root' })
export class AppointmentNotificationService {
  public resourceUrl = SERVER_API_URL + 'api/appointment-notifications';

  constructor(protected http: HttpClient) {}

  create(appointmentNotification: IAppointmentNotification): Observable<EntityResponseType> {
    return this.http.post<IAppointmentNotification>(this.resourceUrl, appointmentNotification, { observe: 'response' });
  }

  update(appointmentNotification: IAppointmentNotification): Observable<EntityResponseType> {
    return this.http.put<IAppointmentNotification>(this.resourceUrl, appointmentNotification, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppointmentNotification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppointmentNotification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
