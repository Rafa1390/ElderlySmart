import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPathologies } from 'app/shared/model/pathologies.model';

type EntityResponseType = HttpResponse<IPathologies>;
type EntityArrayResponseType = HttpResponse<IPathologies[]>;

@Injectable({ providedIn: 'root' })
export class PathologiesService {
  public resourceUrl = SERVER_API_URL + 'api/pathologies';

  constructor(protected http: HttpClient) {}

  create(pathologies: IPathologies): Observable<EntityResponseType> {
    return this.http.post<IPathologies>(this.resourceUrl, pathologies, { observe: 'response' });
  }

  update(pathologies: IPathologies): Observable<EntityResponseType> {
    return this.http.put<IPathologies>(this.resourceUrl, pathologies, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPathologies>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPathologies[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
