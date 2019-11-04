import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MedicalAppointment } from 'app/shared/model/medical-appointment.model';
import { MedicalAppointmentService } from './medical-appointment.service';
import { MedicalAppointmentComponent } from './medical-appointment.component';
import { MedicalAppointmentDetailComponent } from './medical-appointment-detail.component';
import { MedicalAppointmentUpdateComponent } from './medical-appointment-update.component';
import { MedicalAppointmentDeletePopupComponent } from './medical-appointment-delete-dialog.component';
import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';

@Injectable({ providedIn: 'root' })
export class MedicalAppointmentResolve implements Resolve<IMedicalAppointment> {
  constructor(private service: MedicalAppointmentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMedicalAppointment> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MedicalAppointment>) => response.ok),
        map((medicalAppointment: HttpResponse<MedicalAppointment>) => medicalAppointment.body)
      );
    }
    return of(new MedicalAppointment());
  }
}

export const medicalAppointmentRoute: Routes = [
  {
    path: '',
    component: MedicalAppointmentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicalAppointment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicalAppointmentDetailComponent,
    resolve: {
      medicalAppointment: MedicalAppointmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicalAppointment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicalAppointmentUpdateComponent,
    resolve: {
      medicalAppointment: MedicalAppointmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicalAppointment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicalAppointmentUpdateComponent,
    resolve: {
      medicalAppointment: MedicalAppointmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicalAppointment.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const medicalAppointmentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MedicalAppointmentDeletePopupComponent,
    resolve: {
      medicalAppointment: MedicalAppointmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicalAppointment.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
