import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Family } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';
import { FamilyComponent } from './family.component';
import { FamilyDetailComponent } from './family-detail.component';
import { FamilyUpdateComponent } from './family-update.component';
import { FamilyDeletePopupComponent } from './family-delete-dialog.component';
import { IFamily } from 'app/shared/model/family.model';

@Injectable({ providedIn: 'root' })
export class FamilyResolve implements Resolve<IFamily> {
  constructor(private service: FamilyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFamily> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Family>) => response.ok),
        map((family: HttpResponse<Family>) => family.body)
      );
    }
    return of(new Family());
  }
}

export const familyRoute: Routes = [
  {
    path: '',
    component: FamilyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.family.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FamilyDetailComponent,
    resolve: {
      family: FamilyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.family.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FamilyUpdateComponent,
    resolve: {
      family: FamilyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.family.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FamilyUpdateComponent,
    resolve: {
      family: FamilyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.family.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const familyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FamilyDeletePopupComponent,
    resolve: {
      family: FamilyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.family.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
