import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { AllergiesComponent } from './allergies.component';
import { AllergiesDetailComponent } from './allergies-detail.component';
import { AllergiesUpdateComponent } from './allergies-update.component';
import { AllergiesDeletePopupComponent, AllergiesDeleteDialogComponent } from './allergies-delete-dialog.component';
import { allergiesRoute, allergiesPopupRoute } from './allergies.route';

const ENTITY_STATES = [...allergiesRoute, ...allergiesPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllergiesComponent,
    AllergiesDetailComponent,
    AllergiesUpdateComponent,
    AllergiesDeleteDialogComponent,
    AllergiesDeletePopupComponent
  ],
  entryComponents: [AllergiesDeleteDialogComponent]
})
export class ElderlySmartAllergiesModule {}
