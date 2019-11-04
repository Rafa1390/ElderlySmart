import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Prescription } from 'app/shared/model/prescription.model';
import { PrescriptionService } from './prescription.service';
import { PrescriptionComponent } from './prescription.component';
import { PrescriptionDetailComponent } from './prescription-detail.component';
import { PrescriptionUpdateComponent } from './prescription-update.component';
import { PrescriptionDeletePopupComponent } from './prescription-delete-dialog.component';
import { IPrescription } from 'app/shared/model/prescription.model';

@Injectable({ providedIn: 'root' })
export class PrescriptionResolve implements Resolve<IPrescription> {
  constructor(private service: PrescriptionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPrescription> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Prescription>) => response.ok),
        map((prescription: HttpResponse<Prescription>) => prescription.body)
      );
    }
    return of(new Prescription());
  }
}

export const prescriptionRoute: Routes = [
  {
    path: '',
    component: PrescriptionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrescriptionDetailComponent,
    resolve: {
      prescription: PrescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrescriptionUpdateComponent,
    resolve: {
      prescription: PrescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrescriptionUpdateComponent,
    resolve: {
      prescription: PrescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const prescriptionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PrescriptionDeletePopupComponent,
    resolve: {
      prescription: PrescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
