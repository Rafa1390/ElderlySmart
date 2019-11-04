import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { UserAppComponent } from './user-app.component';
import { UserAppDetailComponent } from './user-app-detail.component';
import { UserAppUpdateComponent } from './user-app-update.component';
import { UserAppDeletePopupComponent, UserAppDeleteDialogComponent } from './user-app-delete-dialog.component';
import { userAppRoute, userAppPopupRoute } from './user-app.route';

const ENTITY_STATES = [...userAppRoute, ...userAppPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserAppComponent,
    UserAppDetailComponent,
    UserAppUpdateComponent,
    UserAppDeleteDialogComponent,
    UserAppDeletePopupComponent
  ],
  entryComponents: [UserAppDeleteDialogComponent]
})
export class ElderlySmartUserAppModule {}
