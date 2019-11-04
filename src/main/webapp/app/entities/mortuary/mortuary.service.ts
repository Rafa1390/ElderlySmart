import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMortuary } from 'app/shared/model/mortuary.model';

type EntityResponseType = HttpResponse<IMortuary>;
type EntityArrayResponseType = HttpResponse<IMortuary[]>;

@Injectable({ providedIn: 'root' })
export class MortuaryService {
  public resourceUrl = SERVER_API_URL + 'api/mortuaries';

  constructor(protected http: HttpClient) {}

  create(mortuary: IMortuary): Observable<EntityResponseType> {
    return this.http.post<IMortuary>(this.resourceUrl, mortuary, { observe: 'response' });
  }

  update(mortuary: IMortuary): Observable<EntityResponseType> {
    return this.http.put<IMortuary>(this.resourceUrl, mortuary, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMortuary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMortuary[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
