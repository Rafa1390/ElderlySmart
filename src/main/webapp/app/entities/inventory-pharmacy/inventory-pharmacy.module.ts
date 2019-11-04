import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { InventoryPharmacyComponent } from './inventory-pharmacy.component';
import { InventoryPharmacyDetailComponent } from './inventory-pharmacy-detail.component';
import { InventoryPharmacyUpdateComponent } from './inventory-pharmacy-update.component';
import {
  InventoryPharmacyDeletePopupComponent,
  InventoryPharmacyDeleteDialogComponent
} from './inventory-pharmacy-delete-dialog.component';
import { inventoryPharmacyRoute, inventoryPharmacyPopupRoute } from './inventory-pharmacy.route';

const ENTITY_STATES = [...inventoryPharmacyRoute, ...inventoryPharmacyPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InventoryPharmacyComponent,
    InventoryPharmacyDetailComponent,
    InventoryPharmacyUpdateComponent,
    InventoryPharmacyDeleteDialogComponent,
    InventoryPharmacyDeletePopupComponent
  ],
  entryComponents: [InventoryPharmacyDeleteDialogComponent]
})
export class ElderlySmartInventoryPharmacyModule {}
