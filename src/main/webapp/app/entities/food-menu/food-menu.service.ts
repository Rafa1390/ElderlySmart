import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFoodMenu } from 'app/shared/model/food-menu.model';

type EntityResponseType = HttpResponse<IFoodMenu>;
type EntityArrayResponseType = HttpResponse<IFoodMenu[]>;

@Injectable({ providedIn: 'root' })
export class FoodMenuService {
  public resourceUrl = SERVER_API_URL + 'api/food-menus';

  constructor(protected http: HttpClient) {}

  create(foodMenu: IFoodMenu): Observable<EntityResponseType> {
    return this.http.post<IFoodMenu>(this.resourceUrl, foodMenu, { observe: 'response' });
  }

  update(foodMenu: IFoodMenu): Observable<EntityResponseType> {
    return this.http.put<IFoodMenu>(this.resourceUrl, foodMenu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFoodMenu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFoodMenu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
