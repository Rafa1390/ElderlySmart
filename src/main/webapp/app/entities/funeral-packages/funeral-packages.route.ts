import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FuneralPackages } from 'app/shared/model/funeral-packages.model';
import { FuneralPackagesService } from './funeral-packages.service';
import { FuneralPackagesComponent } from './funeral-packages.component';
import { FuneralPackagesDetailComponent } from './funeral-packages-detail.component';
import { FuneralPackagesUpdateComponent } from './funeral-packages-update.component';
import { FuneralPackagesDeletePopupComponent } from './funeral-packages-delete-dialog.component';
import { IFuneralPackages } from 'app/shared/model/funeral-packages.model';

@Injectable({ providedIn: 'root' })
export class FuneralPackagesResolve implements Resolve<IFuneralPackages> {
  constructor(private service: FuneralPackagesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFuneralPackages> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FuneralPackages>) => response.ok),
        map((funeralPackages: HttpResponse<FuneralPackages>) => funeralPackages.body)
      );
    }
    return of(new FuneralPackages());
  }
}

export const funeralPackagesRoute: Routes = [
  {
    path: '',
    component: FuneralPackagesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralPackages.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FuneralPackagesDetailComponent,
    resolve: {
      funeralPackages: FuneralPackagesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralPackages.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FuneralPackagesUpdateComponent,
    resolve: {
      funeralPackages: FuneralPackagesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralPackages.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FuneralPackagesUpdateComponent,
    resolve: {
      funeralPackages: FuneralPackagesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralPackages.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const funeralPackagesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FuneralPackagesDeletePopupComponent,
    resolve: {
      funeralPackages: FuneralPackagesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralPackages.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
