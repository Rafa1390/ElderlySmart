import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Allergies } from 'app/shared/model/allergies.model';
import { AllergiesService } from './allergies.service';
import { AllergiesComponent } from './allergies.component';
import { AllergiesDetailComponent } from './allergies-detail.component';
import { AllergiesUpdateComponent } from './allergies-update.component';
import { AllergiesDeletePopupComponent } from './allergies-delete-dialog.component';
import { IAllergies } from 'app/shared/model/allergies.model';

@Injectable({ providedIn: 'root' })
export class AllergiesResolve implements Resolve<IAllergies> {
  constructor(private service: AllergiesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllergies> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Allergies>) => response.ok),
        map((allergies: HttpResponse<Allergies>) => allergies.body)
      );
    }
    return of(new Allergies());
  }
}

export const allergiesRoute: Routes = [
  {
    path: '',
    component: AllergiesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.allergies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllergiesDetailComponent,
    resolve: {
      allergies: AllergiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.allergies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllergiesUpdateComponent,
    resolve: {
      allergies: AllergiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.allergies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllergiesUpdateComponent,
    resolve: {
      allergies: AllergiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.allergies.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allergiesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AllergiesDeletePopupComponent,
    resolve: {
      allergies: AllergiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.allergies.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
