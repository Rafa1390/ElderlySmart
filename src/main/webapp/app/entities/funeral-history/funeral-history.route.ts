import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FuneralHistory } from 'app/shared/model/funeral-history.model';
import { FuneralHistoryService } from './funeral-history.service';
import { FuneralHistoryComponent } from './funeral-history.component';
import { FuneralHistoryDetailComponent } from './funeral-history-detail.component';
import { FuneralHistoryUpdateComponent } from './funeral-history-update.component';
import { FuneralHistoryDeletePopupComponent } from './funeral-history-delete-dialog.component';
import { IFuneralHistory } from 'app/shared/model/funeral-history.model';

@Injectable({ providedIn: 'root' })
export class FuneralHistoryResolve implements Resolve<IFuneralHistory> {
  constructor(private service: FuneralHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFuneralHistory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FuneralHistory>) => response.ok),
        map((funeralHistory: HttpResponse<FuneralHistory>) => funeralHistory.body)
      );
    }
    return of(new FuneralHistory());
  }
}

export const funeralHistoryRoute: Routes = [
  {
    path: '',
    component: FuneralHistoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FuneralHistoryDetailComponent,
    resolve: {
      funeralHistory: FuneralHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FuneralHistoryUpdateComponent,
    resolve: {
      funeralHistory: FuneralHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FuneralHistoryUpdateComponent,
    resolve: {
      funeralHistory: FuneralHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const funeralHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FuneralHistoryDeletePopupComponent,
    resolve: {
      funeralHistory: FuneralHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.funeralHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
