import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrescriptionNotification } from 'app/shared/model/prescription-notification.model';

type EntityResponseType = HttpResponse<IPrescriptionNotification>;
type EntityArrayResponseType = HttpResponse<IPrescriptionNotification[]>;

@Injectable({ providedIn: 'root' })
export class PrescriptionNotificationService {
  public resourceUrl = SERVER_API_URL + 'api/prescription-notifications';

  constructor(protected http: HttpClient) {}

  create(prescriptionNotification: IPrescriptionNotification): Observable<EntityResponseType> {
    return this.http.post<IPrescriptionNotification>(this.resourceUrl, prescriptionNotification, { observe: 'response' });
  }

  update(prescriptionNotification: IPrescriptionNotification): Observable<EntityResponseType> {
    return this.http.put<IPrescriptionNotification>(this.resourceUrl, prescriptionNotification, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrescriptionNotification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrescriptionNotification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
