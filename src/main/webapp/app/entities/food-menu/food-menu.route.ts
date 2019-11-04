import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FoodMenu } from 'app/shared/model/food-menu.model';
import { FoodMenuService } from './food-menu.service';
import { FoodMenuComponent } from './food-menu.component';
import { FoodMenuDetailComponent } from './food-menu-detail.component';
import { FoodMenuUpdateComponent } from './food-menu-update.component';
import { FoodMenuDeletePopupComponent } from './food-menu-delete-dialog.component';
import { IFoodMenu } from 'app/shared/model/food-menu.model';

@Injectable({ providedIn: 'root' })
export class FoodMenuResolve implements Resolve<IFoodMenu> {
  constructor(private service: FoodMenuService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFoodMenu> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FoodMenu>) => response.ok),
        map((foodMenu: HttpResponse<FoodMenu>) => foodMenu.body)
      );
    }
    return of(new FoodMenu());
  }
}

export const foodMenuRoute: Routes = [
  {
    path: '',
    component: FoodMenuComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.foodMenu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FoodMenuDetailComponent,
    resolve: {
      foodMenu: FoodMenuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.foodMenu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FoodMenuUpdateComponent,
    resolve: {
      foodMenu: FoodMenuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.foodMenu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FoodMenuUpdateComponent,
    resolve: {
      foodMenu: FoodMenuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.foodMenu.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const foodMenuPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FoodMenuDeletePopupComponent,
    resolve: {
      foodMenu: FoodMenuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.foodMenu.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
