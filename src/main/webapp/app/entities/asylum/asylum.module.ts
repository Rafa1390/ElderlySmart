import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { AsylumComponent } from './asylum.component';
import { AsylumDetailComponent } from './asylum-detail.component';
import { AsylumUpdateComponent } from './asylum-update.component';
import { AsylumDeletePopupComponent, AsylumDeleteDialogComponent } from './asylum-delete-dialog.component';
import { asylumRoute, asylumPopupRoute } from './asylum.route';

const ENTITY_STATES = [...asylumRoute, ...asylumPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AsylumComponent, AsylumDetailComponent, AsylumUpdateComponent, AsylumDeleteDialogComponent, AsylumDeletePopupComponent],
  entryComponents: [AsylumDeleteDialogComponent]
})
export class ElderlySmartAsylumModule {}
