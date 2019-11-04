import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';

type EntityResponseType = HttpResponse<IInventoryPharmacy>;
type EntityArrayResponseType = HttpResponse<IInventoryPharmacy[]>;

@Injectable({ providedIn: 'root' })
export class InventoryPharmacyService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-pharmacies';

  constructor(protected http: HttpClient) {}

  create(inventoryPharmacy: IInventoryPharmacy): Observable<EntityResponseType> {
    return this.http.post<IInventoryPharmacy>(this.resourceUrl, inventoryPharmacy, { observe: 'response' });
  }

  update(inventoryPharmacy: IInventoryPharmacy): Observable<EntityResponseType> {
    return this.http.put<IInventoryPharmacy>(this.resourceUrl, inventoryPharmacy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInventoryPharmacy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInventoryPharmacy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
