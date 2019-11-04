import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';
import { InventoryPharmacyService } from './inventory-pharmacy.service';
import { InventoryPharmacyComponent } from './inventory-pharmacy.component';
import { InventoryPharmacyDetailComponent } from './inventory-pharmacy-detail.component';
import { InventoryPharmacyUpdateComponent } from './inventory-pharmacy-update.component';
import { InventoryPharmacyDeletePopupComponent } from './inventory-pharmacy-delete-dialog.component';
import { IInventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';

@Injectable({ providedIn: 'root' })
export class InventoryPharmacyResolve implements Resolve<IInventoryPharmacy> {
  constructor(private service: InventoryPharmacyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventoryPharmacy> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InventoryPharmacy>) => response.ok),
        map((inventoryPharmacy: HttpResponse<InventoryPharmacy>) => inventoryPharmacy.body)
      );
    }
    return of(new InventoryPharmacy());
  }
}

export const inventoryPharmacyRoute: Routes = [
  {
    path: '',
    component: InventoryPharmacyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryPharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventoryPharmacyDetailComponent,
    resolve: {
      inventoryPharmacy: InventoryPharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryPharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventoryPharmacyUpdateComponent,
    resolve: {
      inventoryPharmacy: InventoryPharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryPharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventoryPharmacyUpdateComponent,
    resolve: {
      inventoryPharmacy: InventoryPharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryPharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inventoryPharmacyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InventoryPharmacyDeletePopupComponent,
    resolve: {
      inventoryPharmacy: InventoryPharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.inventoryPharmacy.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
