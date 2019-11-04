import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { FamilyComponent } from './family.component';
import { FamilyDetailComponent } from './family-detail.component';
import { FamilyUpdateComponent } from './family-update.component';
import { FamilyDeletePopupComponent, FamilyDeleteDialogComponent } from './family-delete-dialog.component';
import { familyRoute, familyPopupRoute } from './family.route';

const ENTITY_STATES = [...familyRoute, ...familyPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FamilyComponent, FamilyDetailComponent, FamilyUpdateComponent, FamilyDeleteDialogComponent, FamilyDeletePopupComponent],
  entryComponents: [FamilyDeleteDialogComponent]
})
export class ElderlySmartFamilyModule {}
