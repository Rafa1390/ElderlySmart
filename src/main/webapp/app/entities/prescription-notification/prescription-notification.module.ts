import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { PrescriptionNotificationComponent } from './prescription-notification.component';
import { PrescriptionNotificationDetailComponent } from './prescription-notification-detail.component';
import { PrescriptionNotificationUpdateComponent } from './prescription-notification-update.component';
import {
  PrescriptionNotificationDeletePopupComponent,
  PrescriptionNotificationDeleteDialogComponent
} from './prescription-notification-delete-dialog.component';
import { prescriptionNotificationRoute, prescriptionNotificationPopupRoute } from './prescription-notification.route';

const ENTITY_STATES = [...prescriptionNotificationRoute, ...prescriptionNotificationPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PrescriptionNotificationComponent,
    PrescriptionNotificationDetailComponent,
    PrescriptionNotificationUpdateComponent,
    PrescriptionNotificationDeleteDialogComponent,
    PrescriptionNotificationDeletePopupComponent
  ],
  entryComponents: [PrescriptionNotificationDeleteDialogComponent]
})
export class ElderlySmartPrescriptionNotificationModule {}
