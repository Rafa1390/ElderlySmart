import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAllergies } from 'app/shared/model/allergies.model';

type EntityResponseType = HttpResponse<IAllergies>;
type EntityArrayResponseType = HttpResponse<IAllergies[]>;

@Injectable({ providedIn: 'root' })
export class AllergiesService {
  public resourceUrl = SERVER_API_URL + 'api/allergies';

  constructor(protected http: HttpClient) {}

  create(allergies: IAllergies): Observable<EntityResponseType> {
    return this.http.post<IAllergies>(this.resourceUrl, allergies, { observe: 'response' });
  }

  update(allergies: IAllergies): Observable<EntityResponseType> {
    return this.http.put<IAllergies>(this.resourceUrl, allergies, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllergies>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllergies[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
