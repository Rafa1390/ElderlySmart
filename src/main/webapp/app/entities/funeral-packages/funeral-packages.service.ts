import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFuneralPackages } from 'app/shared/model/funeral-packages.model';

type EntityResponseType = HttpResponse<IFuneralPackages>;
type EntityArrayResponseType = HttpResponse<IFuneralPackages[]>;

@Injectable({ providedIn: 'root' })
export class FuneralPackagesService {
  public resourceUrl = SERVER_API_URL + 'api/funeral-packages';

  constructor(protected http: HttpClient) {}

  create(funeralPackages: IFuneralPackages): Observable<EntityResponseType> {
    return this.http.post<IFuneralPackages>(this.resourceUrl, funeralPackages, { observe: 'response' });
  }

  update(funeralPackages: IFuneralPackages): Observable<EntityResponseType> {
    return this.http.put<IFuneralPackages>(this.resourceUrl, funeralPackages, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuneralPackages>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuneralPackages[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
