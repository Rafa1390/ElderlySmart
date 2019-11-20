import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { AsylumComponent } from './asylum.component';
import { AsylumDetailComponent } from './asylum-detail.component';
import { AsylumUpdateComponent } from './asylum-update.component';
import { AsylumDeletePopupComponent, AsylumDeleteDialogComponent } from './asylum-delete-dialog.component';
import { asylumRoute, asylumPopupRoute } from './asylum.route';
import { AsylumFilter } from 'app/entities/asylum/asylum-filter';
import { FormsModule } from '@angular/forms';

const ENTITY_STATES = [...asylumRoute, ...asylumPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, FormsModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AsylumComponent,
    AsylumDetailComponent,
    AsylumUpdateComponent,
    AsylumDeleteDialogComponent,
    AsylumDeletePopupComponent,
    AsylumFilter
  ],
  entryComponents: [AsylumDeleteDialogComponent]
})
export class ElderlySmartAsylumModule {}
