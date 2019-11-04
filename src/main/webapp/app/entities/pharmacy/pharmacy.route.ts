import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from './pharmacy.service';
import { PharmacyComponent } from './pharmacy.component';
import { PharmacyDetailComponent } from './pharmacy-detail.component';
import { PharmacyUpdateComponent } from './pharmacy-update.component';
import { PharmacyDeletePopupComponent } from './pharmacy-delete-dialog.component';
import { IPharmacy } from 'app/shared/model/pharmacy.model';

@Injectable({ providedIn: 'root' })
export class PharmacyResolve implements Resolve<IPharmacy> {
  constructor(private service: PharmacyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPharmacy> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pharmacy>) => response.ok),
        map((pharmacy: HttpResponse<Pharmacy>) => pharmacy.body)
      );
    }
    return of(new Pharmacy());
  }
}

export const pharmacyRoute: Routes = [
  {
    path: '',
    component: PharmacyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PharmacyDetailComponent,
    resolve: {
      pharmacy: PharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PharmacyUpdateComponent,
    resolve: {
      pharmacy: PharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PharmacyUpdateComponent,
    resolve: {
      pharmacy: PharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacy.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pharmacyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PharmacyDeletePopupComponent,
    resolve: {
      pharmacy: PharmacyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacy.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
