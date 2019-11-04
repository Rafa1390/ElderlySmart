import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { PharmacyComponent } from './pharmacy.component';
import { PharmacyDetailComponent } from './pharmacy-detail.component';
import { PharmacyUpdateComponent } from './pharmacy-update.component';
import { PharmacyDeletePopupComponent, PharmacyDeleteDialogComponent } from './pharmacy-delete-dialog.component';
import { pharmacyRoute, pharmacyPopupRoute } from './pharmacy.route';

const ENTITY_STATES = [...pharmacyRoute, ...pharmacyPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PharmacyComponent,
    PharmacyDetailComponent,
    PharmacyUpdateComponent,
    PharmacyDeleteDialogComponent,
    PharmacyDeletePopupComponent
  ],
  entryComponents: [PharmacyDeleteDialogComponent]
})
export class ElderlySmartPharmacyModule {}
