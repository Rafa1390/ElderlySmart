import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { ElderlyComponent } from './elderly.component';
import { ElderlyDetailComponent } from './elderly-detail.component';
import { ElderlyUpdateComponent } from './elderly-update.component';
import { ElderlyDeletePopupComponent, ElderlyDeleteDialogComponent } from './elderly-delete-dialog.component';
import { elderlyRoute, elderlyPopupRoute } from './elderly.route';

const ENTITY_STATES = [...elderlyRoute, ...elderlyPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ElderlyComponent,
    ElderlyDetailComponent,
    ElderlyUpdateComponent,
    ElderlyDeleteDialogComponent,
    ElderlyDeletePopupComponent
  ],
  entryComponents: [ElderlyDeleteDialogComponent]
})
export class ElderlySmartElderlyModule {}
