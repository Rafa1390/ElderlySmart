import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CaseFile } from 'app/shared/model/case-file.model';
import { CaseFileService } from './case-file.service';
import { CaseFileComponent } from './case-file.component';
import { CaseFileDetailComponent } from './case-file-detail.component';
import { CaseFileUpdateComponent } from './case-file-update.component';
import { CaseFileDeletePopupComponent } from './case-file-delete-dialog.component';
import { ICaseFile } from 'app/shared/model/case-file.model';

@Injectable({ providedIn: 'root' })
export class CaseFileResolve implements Resolve<ICaseFile> {
  constructor(private service: CaseFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICaseFile> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CaseFile>) => response.ok),
        map((caseFile: HttpResponse<CaseFile>) => caseFile.body)
      );
    }
    return of(new CaseFile());
  }
}

export const caseFileRoute: Routes = [
  {
    path: '',
    component: CaseFileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.caseFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CaseFileDetailComponent,
    resolve: {
      caseFile: CaseFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.caseFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CaseFileUpdateComponent,
    resolve: {
      caseFile: CaseFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.caseFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CaseFileUpdateComponent,
    resolve: {
      caseFile: CaseFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.caseFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const caseFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CaseFileDeletePopupComponent,
    resolve: {
      caseFile: CaseFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'elderlySmartApp.caseFile.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
