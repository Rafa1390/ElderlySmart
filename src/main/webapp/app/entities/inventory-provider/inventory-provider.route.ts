import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventoryProvider } from 'app/shared/model/inventory-provider.model';
import { InventoryProviderService } from './inventory-provider.service';
import { InventoryProviderComponent } from './inventory-provider.component';
import { InventoryProviderDetailComponent } from './inventory-provider-detail.component';
import { InventoryProviderUpdateComponent } from './inventory-provider-update.component';
import { InventoryProviderDeletePopupComponent } from './inventory-provider-delete-dialog.component';
import { IInventoryProvider } from 'app/shared/model/inventory-provider.model';

@Injectable({ providedIn: 'root' })
export class InventoryProviderResolve implements Resolve<IInventoryProvider> {
  constructor(private service: InventoryProviderService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventoryProvider> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InventoryProvider>) => response.ok),
        map((inventoryProvider: HttpResponse<InventoryProvider>) => inventoryProvider.body)
      );
    }
    return of(new InventoryProvider());
  }
}

export const inventoryProviderRoute: Routes = [
  {
    path: '',
    component: InventoryProviderComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryProvider.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventoryProviderDetailComponent,
    resolve: {
      inventoryProvider: InventoryProviderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryProvider.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventoryProviderUpdateComponent,
    resolve: {
      inventoryProvider: InventoryProviderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryProvider.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventoryProviderUpdateComponent,
    resolve: {
      inventoryProvider: InventoryProviderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryProvider.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inventoryProviderPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InventoryProviderDeletePopupComponent,
    resolve: {
      inventoryProvider: InventoryProviderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryProvider.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
