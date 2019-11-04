import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Elderly } from 'app/shared/model/elderly.model';
import { ElderlyService } from './elderly.service';
import { ElderlyComponent } from './elderly.component';
import { ElderlyDetailComponent } from './elderly-detail.component';
import { ElderlyUpdateComponent } from './elderly-update.component';
import { ElderlyDeletePopupComponent } from './elderly-delete-dialog.component';
import { IElderly } from 'app/shared/model/elderly.model';

@Injectable({ providedIn: 'root' })
export class ElderlyResolve implements Resolve<IElderly> {
  constructor(private service: ElderlyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IElderly> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Elderly>) => response.ok),
        map((elderly: HttpResponse<Elderly>) => elderly.body)
      );
    }
    return of(new Elderly());
  }
}

export const elderlyRoute: Routes = [
  {
    path: '',
    component: ElderlyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.elderly.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ElderlyDetailComponent,
    resolve: {
      elderly: ElderlyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.elderly.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ElderlyUpdateComponent,
    resolve: {
      elderly: ElderlyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.elderly.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ElderlyUpdateComponent,
    resolve: {
      elderly: ElderlyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.elderly.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const elderlyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ElderlyDeletePopupComponent,
    resolve: {
      elderly: ElderlyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.elderly.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
