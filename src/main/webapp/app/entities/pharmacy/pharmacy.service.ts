import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPharmacy } from 'app/shared/model/pharmacy.model';

type EntityResponseType = HttpResponse<IPharmacy>;
type EntityArrayResponseType = HttpResponse<IPharmacy[]>;

@Injectable({ providedIn: 'root' })
export class PharmacyService {
  public resourceUrl = SERVER_API_URL + 'api/pharmacies';

  constructor(protected http: HttpClient) {}

  create(pharmacy: IPharmacy): Observable<EntityResponseType> {
    return this.http.post<IPharmacy>(this.resourceUrl, pharmacy, { observe: 'response' });
  }

  update(pharmacy: IPharmacy): Observable<EntityResponseType> {
    return this.http.put<IPharmacy>(this.resourceUrl, pharmacy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPharmacy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPharmacy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
