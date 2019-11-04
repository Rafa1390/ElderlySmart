import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pathologies } from 'app/shared/model/pathologies.model';
import { PathologiesService } from './pathologies.service';
import { PathologiesComponent } from './pathologies.component';
import { PathologiesDetailComponent } from './pathologies-detail.component';
import { PathologiesUpdateComponent } from './pathologies-update.component';
import { PathologiesDeletePopupComponent } from './pathologies-delete-dialog.component';
import { IPathologies } from 'app/shared/model/pathologies.model';

@Injectable({ providedIn: 'root' })
export class PathologiesResolve implements Resolve<IPathologies> {
  constructor(private service: PathologiesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPathologies> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pathologies>) => response.ok),
        map((pathologies: HttpResponse<Pathologies>) => pathologies.body)
      );
    }
    return of(new Pathologies());
  }
}

export const pathologiesRoute: Routes = [
  {
    path: '',
    component: PathologiesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pathologies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PathologiesDetailComponent,
    resolve: {
      pathologies: PathologiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pathologies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PathologiesUpdateComponent,
    resolve: {
      pathologies: PathologiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pathologies.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PathologiesUpdateComponent,
    resolve: {
      pathologies: PathologiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pathologies.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pathologiesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PathologiesDeletePopupComponent,
    resolve: {
      pathologies: PathologiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.pathologies.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
