import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { InventoryProviderComponent } from './inventory-provider.component';
import { InventoryProviderDetailComponent } from './inventory-provider-detail.component';
import { InventoryProviderUpdateComponent } from './inventory-provider-update.component';
import {
  InventoryProviderDeletePopupComponent,
  InventoryProviderDeleteDialogComponent
} from './inventory-provider-delete-dialog.component';
import { inventoryProviderRoute, inventoryProviderPopupRoute } from './inventory-provider.route';

const ENTITY_STATES = [...inventoryProviderRoute, ...inventoryProviderPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InventoryProviderComponent,
    InventoryProviderDetailComponent,
    InventoryProviderUpdateComponent,
    InventoryProviderDeleteDialogComponent,
    InventoryProviderDeletePopupComponent
  ],
  entryComponents: [InventoryProviderDeleteDialogComponent]
})
export class ElderlySmartInventoryProviderModule {}
