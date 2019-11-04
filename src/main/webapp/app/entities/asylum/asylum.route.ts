import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Asylum } from 'app/shared/model/asylum.model';
import { AsylumService } from './asylum.service';
import { AsylumComponent } from './asylum.component';
import { AsylumDetailComponent } from './asylum-detail.component';
import { AsylumUpdateComponent } from './asylum-update.component';
import { AsylumDeletePopupComponent } from './asylum-delete-dialog.component';
import { IAsylum } from 'app/shared/model/asylum.model';

@Injectable({ providedIn: 'root' })
export class AsylumResolve implements Resolve<IAsylum> {
  constructor(private service: AsylumService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAsylum> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Asylum>) => response.ok),
        map((asylum: HttpResponse<Asylum>) => asylum.body)
      );
    }
    return of(new Asylum());
  }
}

export const asylumRoute: Routes = [
  {
    path: '',
    component: AsylumComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.asylum.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AsylumDetailComponent,
    resolve: {
      asylum: AsylumResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.asylum.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AsylumUpdateComponent,
    resolve: {
      asylum: AsylumResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.asylum.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AsylumUpdateComponent,
    resolve: {
      asylum: AsylumResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.asylum.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const asylumPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AsylumDeletePopupComponent,
    resolve: {
      asylum: AsylumResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.asylum.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
