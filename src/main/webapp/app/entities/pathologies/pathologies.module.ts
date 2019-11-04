import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { PathologiesComponent } from './pathologies.component';
import { PathologiesDetailComponent } from './pathologies-detail.component';
import { PathologiesUpdateComponent } from './pathologies-update.component';
import { PathologiesDeletePopupComponent, PathologiesDeleteDialogComponent } from './pathologies-delete-dialog.component';
import { pathologiesRoute, pathologiesPopupRoute } from './pathologies.route';

const ENTITY_STATES = [...pathologiesRoute, ...pathologiesPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PathologiesComponent,
    PathologiesDetailComponent,
    PathologiesUpdateComponent,
    PathologiesDeleteDialogComponent,
    PathologiesDeletePopupComponent
  ],
  entryComponents: [PathologiesDeleteDialogComponent]
})
export class ElderlySmartPathologiesModule {}
