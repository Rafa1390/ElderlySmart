import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProviderHistory } from 'app/shared/model/provider-history.model';
import { ProviderHistoryService } from './provider-history.service';
import { ProviderHistoryComponent } from './provider-history.component';
import { ProviderHistoryDetailComponent } from './provider-history-detail.component';
import { ProviderHistoryUpdateComponent } from './provider-history-update.component';
import { ProviderHistoryDeletePopupComponent } from './provider-history-delete-dialog.component';
import { IProviderHistory } from 'app/shared/model/provider-history.model';

@Injectable({ providedIn: 'root' })
export class ProviderHistoryResolve implements Resolve<IProviderHistory> {
  constructor(private service: ProviderHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProviderHistory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProviderHistory>) => response.ok),
        map((providerHistory: HttpResponse<ProviderHistory>) => providerHistory.body)
      );
    }
    return of(new ProviderHistory());
  }
}

export const providerHistoryRoute: Routes = [
  {
    path: '',
    component: ProviderHistoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.providerHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProviderHistoryDetailComponent,
    resolve: {
      providerHistory: ProviderHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.providerHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProviderHistoryUpdateComponent,
    resolve: {
      providerHistory: ProviderHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.providerHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProviderHistoryUpdateComponent,
    resolve: {
      providerHistory: ProviderHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.providerHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const providerHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProviderHistoryDeletePopupComponent,
    resolve: {
      providerHistory: ProviderHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.providerHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
