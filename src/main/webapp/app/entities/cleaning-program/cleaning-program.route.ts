import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CleaningProgram } from 'app/shared/model/cleaning-program.model';
import { CleaningProgramService } from './cleaning-program.service';
import { CleaningProgramComponent } from './cleaning-program.component';
import { CleaningProgramDetailComponent } from './cleaning-program-detail.component';
import { CleaningProgramUpdateComponent } from './cleaning-program-update.component';
import { CleaningProgramDeletePopupComponent } from './cleaning-program-delete-dialog.component';
import { ICleaningProgram } from 'app/shared/model/cleaning-program.model';

@Injectable({ providedIn: 'root' })
export class CleaningProgramResolve implements Resolve<ICleaningProgram> {
  constructor(private service: CleaningProgramService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICleaningProgram> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CleaningProgram>) => response.ok),
        map((cleaningProgram: HttpResponse<CleaningProgram>) => cleaningProgram.body)
      );
    }
    return of(new CleaningProgram());
  }
}

export const cleaningProgramRoute: Routes = [
  {
    path: '',
    component: CleaningProgramComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.cleaningProgram.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CleaningProgramDetailComponent,
    resolve: {
      cleaningProgram: CleaningProgramResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.cleaningProgram.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CleaningProgramUpdateComponent,
    resolve: {
      cleaningProgram: CleaningProgramResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.cleaningProgram.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CleaningProgramUpdateComponent,
    resolve: {
      cleaningProgram: CleaningProgramResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.cleaningProgram.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cleaningProgramPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CleaningProgramDeletePopupComponent,
    resolve: {
      cleaningProgram: CleaningProgramResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.cleaningProgram.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
