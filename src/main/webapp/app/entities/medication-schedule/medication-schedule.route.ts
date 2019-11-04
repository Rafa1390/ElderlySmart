import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MedicationSchedule } from 'app/shared/model/medication-schedule.model';
import { MedicationScheduleService } from './medication-schedule.service';
import { MedicationScheduleComponent } from './medication-schedule.component';
import { MedicationScheduleDetailComponent } from './medication-schedule-detail.component';
import { MedicationScheduleUpdateComponent } from './medication-schedule-update.component';
import { MedicationScheduleDeletePopupComponent } from './medication-schedule-delete-dialog.component';
import { IMedicationSchedule } from 'app/shared/model/medication-schedule.model';

@Injectable({ providedIn: 'root' })
export class MedicationScheduleResolve implements Resolve<IMedicationSchedule> {
  constructor(private service: MedicationScheduleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMedicationSchedule> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MedicationSchedule>) => response.ok),
        map((medicationSchedule: HttpResponse<MedicationSchedule>) => medicationSchedule.body)
      );
    }
    return of(new MedicationSchedule());
  }
}

export const medicationScheduleRoute: Routes = [
  {
    path: '',
    component: MedicationScheduleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicationSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicationScheduleDetailComponent,
    resolve: {
      medicationSchedule: MedicationScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicationSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicationScheduleUpdateComponent,
    resolve: {
      medicationSchedule: MedicationScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicationSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicationScheduleUpdateComponent,
    resolve: {
      medicationSchedule: MedicationScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicationSchedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const medicationSchedulePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MedicationScheduleDeletePopupComponent,
    resolve: {
      medicationSchedule: MedicationScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.medicationSchedule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
