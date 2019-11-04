import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PrescriptionNotification } from 'app/shared/model/prescription-notification.model';
import { PrescriptionNotificationService } from './prescription-notification.service';
import { PrescriptionNotificationComponent } from './prescription-notification.component';
import { PrescriptionNotificationDetailComponent } from './prescription-notification-detail.component';
import { PrescriptionNotificationUpdateComponent } from './prescription-notification-update.component';
import { PrescriptionNotificationDeletePopupComponent } from './prescription-notification-delete-dialog.component';
import { IPrescriptionNotification } from 'app/shared/model/prescription-notification.model';

@Injectable({ providedIn: 'root' })
export class PrescriptionNotificationResolve implements Resolve<IPrescriptionNotification> {
  constructor(private service: PrescriptionNotificationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPrescriptionNotification> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PrescriptionNotification>) => response.ok),
        map((prescriptionNotification: HttpResponse<PrescriptionNotification>) => prescriptionNotification.body)
      );
    }
    return of(new PrescriptionNotification());
  }
}

export const prescriptionNotificationRoute: Routes = [
  {
    path: '',
    component: PrescriptionNotificationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescriptionNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrescriptionNotificationDetailComponent,
    resolve: {
      prescriptionNotification: PrescriptionNotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescriptionNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrescriptionNotificationUpdateComponent,
    resolve: {
      prescriptionNotification: PrescriptionNotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescriptionNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrescriptionNotificationUpdateComponent,
    resolve: {
      prescriptionNotification: PrescriptionNotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescriptionNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const prescriptionNotificationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PrescriptionNotificationDeletePopupComponent,
    resolve: {
      prescriptionNotification: PrescriptionNotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.prescriptionNotification.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
