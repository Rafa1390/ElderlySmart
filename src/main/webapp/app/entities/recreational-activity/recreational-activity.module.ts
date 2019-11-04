import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { RecreationalActivityComponent } from './recreational-activity.component';
import { RecreationalActivityDetailComponent } from './recreational-activity-detail.component';
import { RecreationalActivityUpdateComponent } from './recreational-activity-update.component';
import {
  RecreationalActivityDeletePopupComponent,
  RecreationalActivityDeleteDialogComponent
} from './recreational-activity-delete-dialog.component';
import { recreationalActivityRoute, recreationalActivityPopupRoute } from './recreational-activity.route';

const ENTITY_STATES = [...recreationalActivityRoute, ...recreationalActivityPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RecreationalActivityComponent,
    RecreationalActivityDetailComponent,
    RecreationalActivityUpdateComponent,
    RecreationalActivityDeleteDialogComponent,
    RecreationalActivityDeletePopupComponent
  ],
  entryComponents: [RecreationalActivityDeleteDialogComponent]
})
export class ElderlySmartRecreationalActivityModule {}
