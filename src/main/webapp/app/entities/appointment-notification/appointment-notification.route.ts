import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AppointmentNotification } from 'app/shared/model/appointment-notification.model';
import { AppointmentNotificationService } from './appointment-notification.service';
import { AppointmentNotificationComponent } from './appointment-notification.component';
import { AppointmentNotificationDetailComponent } from './appointment-notification-detail.component';
import { AppointmentNotificationUpdateComponent } from './appointment-notification-update.component';
import { AppointmentNotificationDeletePopupComponent } from './appointment-notification-delete-dialog.component';
import { IAppointmentNotification } from 'app/shared/model/appointment-notification.model';

@Injectable({ providedIn: 'root' })
export class AppointmentNotificationResolve implements Resolve<IAppointmentNotification> {
  constructor(private service: AppointmentNotificationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppointmentNotification> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AppointmentNotification>) => response.ok),
        map((appointmentNotification: HttpResponse<AppointmentNotification>) => appointmentNotification.body)
      );
    }
    return of(new AppointmentNotification());
  }
}

export const appointmentNotificationRoute: Routes = [
  {
    path: '',
    component: AppointmentNotificationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.appointmentNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AppointmentNotificationDetailComponent,
    resolve: {
      appointmentNotification: AppointmentNotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.appointmentNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AppointmentNotificationUpdateComponent,
    resolve: {
      appointmentNotification: AppointmentNotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.appointmentNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AppointmentNotificationUpdateComponent,
    resolve: {
      appointmentNotification: AppointmentNotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.appointmentNotification.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const appointmentNotificationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AppointmentNotificationDeletePopupComponent,
    resolve: {
      appointmentNotification: AppointmentNotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.appointmentNotification.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
