import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAsylum } from 'app/shared/model/asylum.model';

type EntityResponseType = HttpResponse<IAsylum>;
type EntityArrayResponseType = HttpResponse<IAsylum[]>;

@Injectable({ providedIn: 'root' })
export class AsylumService {
  public resourceUrl = SERVER_API_URL + 'api/asylums';

  constructor(protected http: HttpClient) {}

  create(asylum: IAsylum): Observable<EntityResponseType> {
    return this.http.post<IAsylum>(this.resourceUrl, asylum, { observe: 'response' });
  }

  update(asylum: IAsylum): Observable<EntityResponseType> {
    return this.http.put<IAsylum>(this.resourceUrl, asylum, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAsylum>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAsylum[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
