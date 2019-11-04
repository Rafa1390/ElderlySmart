import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DonationHistory } from 'app/shared/model/donation-history.model';
import { DonationHistoryService } from './donation-history.service';
import { DonationHistoryComponent } from './donation-history.component';
import { DonationHistoryDetailComponent } from './donation-history-detail.component';
import { DonationHistoryUpdateComponent } from './donation-history-update.component';
import { DonationHistoryDeletePopupComponent } from './donation-history-delete-dialog.component';
import { IDonationHistory } from 'app/shared/model/donation-history.model';

@Injectable({ providedIn: 'root' })
export class DonationHistoryResolve implements Resolve<IDonationHistory> {
  constructor(private service: DonationHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDonationHistory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DonationHistory>) => response.ok),
        map((donationHistory: HttpResponse<DonationHistory>) => donationHistory.body)
      );
    }
    return of(new DonationHistory());
  }
}

export const donationHistoryRoute: Routes = [
  {
    path: '',
    component: DonationHistoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.donationHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DonationHistoryDetailComponent,
    resolve: {
      donationHistory: DonationHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.donationHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DonationHistoryUpdateComponent,
    resolve: {
      donationHistory: DonationHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.donationHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DonationHistoryUpdateComponent,
    resolve: {
      donationHistory: DonationHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.donationHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const donationHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DonationHistoryDeletePopupComponent,
    resolve: {
      donationHistory: DonationHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.donationHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
