import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { PartnerComponent } from './partner.component';
import { PartnerDetailComponent } from './partner-detail.component';
import { PartnerUpdateComponent } from './partner-update.component';
import { PartnerDeletePopupComponent, PartnerDeleteDialogComponent } from './partner-delete-dialog.component';
import { partnerRoute, partnerPopupRoute } from './partner.route';

const ENTITY_STATES = [...partnerRoute, ...partnerPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PartnerComponent,
    PartnerDetailComponent,
    PartnerUpdateComponent,
    PartnerDeleteDialogComponent,
    PartnerDeletePopupComponent
  ],
  entryComponents: [PartnerDeleteDialogComponent]
})
export class ElderlySmartPartnerModule {}
