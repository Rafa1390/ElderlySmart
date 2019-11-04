import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Mortuary } from 'app/shared/model/mortuary.model';
import { MortuaryService } from './mortuary.service';
import { MortuaryComponent } from './mortuary.component';
import { MortuaryDetailComponent } from './mortuary-detail.component';
import { MortuaryUpdateComponent } from './mortuary-update.component';
import { MortuaryDeletePopupComponent } from './mortuary-delete-dialog.component';
import { IMortuary } from 'app/shared/model/mortuary.model';

@Injectable({ providedIn: 'root' })
export class MortuaryResolve implements Resolve<IMortuary> {
  constructor(private service: MortuaryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMortuary> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Mortuary>) => response.ok),
        map((mortuary: HttpResponse<Mortuary>) => mortuary.body)
      );
    }
    return of(new Mortuary());
  }
}

export const mortuaryRoute: Routes = [
  {
    path: '',
    component: MortuaryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.mortuary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MortuaryDetailComponent,
    resolve: {
      mortuary: MortuaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.mortuary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MortuaryUpdateComponent,
    resolve: {
      mortuary: MortuaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.mortuary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MortuaryUpdateComponent,
    resolve: {
      mortuary: MortuaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.mortuary.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const mortuaryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MortuaryDeletePopupComponent,
    resolve: {
      mortuary: MortuaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.mortuary.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
