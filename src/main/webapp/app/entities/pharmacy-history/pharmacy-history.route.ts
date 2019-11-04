import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PharmacyHistory } from 'app/shared/model/pharmacy-history.model';
import { PharmacyHistoryService } from './pharmacy-history.service';
import { PharmacyHistoryComponent } from './pharmacy-history.component';
import { PharmacyHistoryDetailComponent } from './pharmacy-history-detail.component';
import { PharmacyHistoryUpdateComponent } from './pharmacy-history-update.component';
import { PharmacyHistoryDeletePopupComponent } from './pharmacy-history-delete-dialog.component';
import { IPharmacyHistory } from 'app/shared/model/pharmacy-history.model';

@Injectable({ providedIn: 'root' })
export class PharmacyHistoryResolve implements Resolve<IPharmacyHistory> {
  constructor(private service: PharmacyHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPharmacyHistory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PharmacyHistory>) => response.ok),
        map((pharmacyHistory: HttpResponse<PharmacyHistory>) => pharmacyHistory.body)
      );
    }
    return of(new PharmacyHistory());
  }
}

export const pharmacyHistoryRoute: Routes = [
  {
    path: '',
    component: PharmacyHistoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacyHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PharmacyHistoryDetailComponent,
    resolve: {
      pharmacyHistory: PharmacyHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacyHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PharmacyHistoryUpdateComponent,
    resolve: {
      pharmacyHistory: PharmacyHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacyHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PharmacyHistoryUpdateComponent,
    resolve: {
      pharmacyHistory: PharmacyHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacyHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pharmacyHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PharmacyHistoryDeletePopupComponent,
    resolve: {
      pharmacyHistory: PharmacyHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pharmacyHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
