import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserApp } from 'app/shared/model/user-app.model';
import { UserAppService } from './user-app.service';
import { UserAppComponent } from './user-app.component';
import { UserAppDetailComponent } from './user-app-detail.component';
import { UserAppUpdateComponent } from './user-app-update.component';
import { UserAppDeletePopupComponent } from './user-app-delete-dialog.component';
import { IUserApp } from 'app/shared/model/user-app.model';

@Injectable({ providedIn: 'root' })
export class UserAppResolve implements Resolve<IUserApp> {
  constructor(private service: UserAppService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserApp> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserApp>) => response.ok),
        map((userApp: HttpResponse<UserApp>) => userApp.body)
      );
    }
    return of(new UserApp());
  }
}

export const userAppRoute: Routes = [
  {
    path: '',
    component: UserAppComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.userApp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserAppDetailComponent,
    resolve: {
      userApp: UserAppResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.userApp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserAppUpdateComponent,
    resolve: {
      userApp: UserAppResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.userApp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserAppUpdateComponent,
    resolve: {
      userApp: UserAppResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.userApp.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userAppPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserAppDeletePopupComponent,
    resolve: {
      userApp: UserAppResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.userApp.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
