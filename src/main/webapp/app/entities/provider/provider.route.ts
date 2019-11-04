import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Provider } from 'app/shared/model/provider.model';
import { ProviderService } from './provider.service';
import { ProviderComponent } from './provider.component';
import { ProviderDetailComponent } from './provider-detail.component';
import { ProviderUpdateComponent } from './provider-update.component';
import { ProviderDeletePopupComponent } from './provider-delete-dialog.component';
import { IProvider } from 'app/shared/model/provider.model';

@Injectable({ providedIn: 'root' })
export class ProviderResolve implements Resolve<IProvider> {
  constructor(private service: ProviderService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProvider> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Provider>) => response.ok),
        map((provider: HttpResponse<Provider>) => provider.body)
      );
    }
    return of(new Provider());
  }
}

export const providerRoute: Routes = [
  {
    path: '',
    component: ProviderComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.provider.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProviderDetailComponent,
    resolve: {
      provider: ProviderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.provider.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProviderUpdateComponent,
    resolve: {
      provider: ProviderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.provider.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProviderUpdateComponent,
    resolve: {
      provider: ProviderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.provider.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const providerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProviderDeletePopupComponent,
    resolve: {
      provider: ProviderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.provider.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
