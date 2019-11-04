import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { AppointmentNotificationComponent } from './appointment-notification.component';
import { AppointmentNotificationDetailComponent } from './appointment-notification-detail.component';
import { AppointmentNotificationUpdateComponent } from './appointment-notification-update.component';
import {
  AppointmentNotificationDeletePopupComponent,
  AppointmentNotificationDeleteDialogComponent
} from './appointment-notification-delete-dialog.component';
import { appointmentNotificationRoute, appointmentNotificationPopupRoute } from './appointment-notification.route';

const ENTITY_STATES = [...appointmentNotificationRoute, ...appointmentNotificationPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AppointmentNotificationComponent,
    AppointmentNotificationDetailComponent,
    AppointmentNotificationUpdateComponent,
    AppointmentNotificationDeleteDialogComponent,
    AppointmentNotificationDeletePopupComponent
  ],
  entryComponents: [AppointmentNotificationDeleteDialogComponent]
})
export class ElderlySmartAppointmentNotificationModule {}
