import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { PharmacyHistoryComponent } from './pharmacy-history.component';
import { PharmacyHistoryDetailComponent } from './pharmacy-history-detail.component';
import { PharmacyHistoryUpdateComponent } from './pharmacy-history-update.component';
import { PharmacyHistoryDeletePopupComponent, PharmacyHistoryDeleteDialogComponent } from './pharmacy-history-delete-dialog.component';
import { pharmacyHistoryRoute, pharmacyHistoryPopupRoute } from './pharmacy-history.route';

const ENTITY_STATES = [...pharmacyHistoryRoute, ...pharmacyHistoryPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PharmacyHistoryComponent,
    PharmacyHistoryDetailComponent,
    PharmacyHistoryUpdateComponent,
    PharmacyHistoryDeleteDialogComponent,
    PharmacyHistoryDeletePopupComponent
  ],
  entryComponents: [PharmacyHistoryDeleteDialogComponent]
})
export class ElderlySmartPharmacyHistoryModule {}
