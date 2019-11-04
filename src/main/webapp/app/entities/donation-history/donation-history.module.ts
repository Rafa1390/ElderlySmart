import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { DonationHistoryComponent } from './donation-history.component';
import { DonationHistoryDetailComponent } from './donation-history-detail.component';
import { DonationHistoryUpdateComponent } from './donation-history-update.component';
import { DonationHistoryDeletePopupComponent, DonationHistoryDeleteDialogComponent } from './donation-history-delete-dialog.component';
import { donationHistoryRoute, donationHistoryPopupRoute } from './donation-history.route';

const ENTITY_STATES = [...donationHistoryRoute, ...donationHistoryPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DonationHistoryComponent,
    DonationHistoryDetailComponent,
    DonationHistoryUpdateComponent,
    DonationHistoryDeleteDialogComponent,
    DonationHistoryDeletePopupComponent
  ],
  entryComponents: [DonationHistoryDeleteDialogComponent]
})
export class ElderlySmartDonationHistoryModule {}
