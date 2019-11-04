import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { MedicalAppointmentComponent } from './medical-appointment.component';
import { MedicalAppointmentDetailComponent } from './medical-appointment-detail.component';
import { MedicalAppointmentUpdateComponent } from './medical-appointment-update.component';
import {
  MedicalAppointmentDeletePopupComponent,
  MedicalAppointmentDeleteDialogComponent
} from './medical-appointment-delete-dialog.component';
import { medicalAppointmentRoute, medicalAppointmentPopupRoute } from './medical-appointment.route';

const ENTITY_STATES = [...medicalAppointmentRoute, ...medicalAppointmentPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MedicalAppointmentComponent,
    MedicalAppointmentDetailComponent,
    MedicalAppointmentUpdateComponent,
    MedicalAppointmentDeleteDialogComponent,
    MedicalAppointmentDeletePopupComponent
  ],
  entryComponents: [MedicalAppointmentDeleteDialogComponent]
})
export class ElderlySmartMedicalAppointmentModule {}
