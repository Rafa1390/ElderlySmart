import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RecreationalActivity } from 'app/shared/model/recreational-activity.model';
import { RecreationalActivityService } from './recreational-activity.service';
import { RecreationalActivityComponent } from './recreational-activity.component';
import { RecreationalActivityDetailComponent } from './recreational-activity-detail.component';
import { RecreationalActivityUpdateComponent } from './recreational-activity-update.component';
import { RecreationalActivityDeletePopupComponent } from './recreational-activity-delete-dialog.component';
import { IRecreationalActivity } from 'app/shared/model/recreational-activity.model';

@Injectable({ providedIn: 'root' })
export class RecreationalActivityResolve implements Resolve<IRecreationalActivity> {
  constructor(private service: RecreationalActivityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecreationalActivity> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RecreationalActivity>) => response.ok),
        map((recreationalActivity: HttpResponse<RecreationalActivity>) => recreationalActivity.body)
      );
    }
    return of(new RecreationalActivity());
  }
}

export const recreationalActivityRoute: Routes = [
  {
    path: '',
    component: RecreationalActivityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.recreationalActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RecreationalActivityDetailComponent,
    resolve: {
      recreationalActivity: RecreationalActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.recreationalActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RecreationalActivityUpdateComponent,
    resolve: {
      recreationalActivity: RecreationalActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.recreationalActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RecreationalActivityUpdateComponent,
    resolve: {
      recreationalActivity: RecreationalActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.recreationalActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const recreationalActivityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RecreationalActivityDeletePopupComponent,
    resolve: {
      recreationalActivity: RecreationalActivityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.recreationalActivity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
