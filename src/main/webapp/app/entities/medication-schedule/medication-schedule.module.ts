import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { MedicationScheduleComponent } from './medication-schedule.component';
import { MedicationScheduleDetailComponent } from './medication-schedule-detail.component';
import { MedicationScheduleUpdateComponent } from './medication-schedule-update.component';
import {
  MedicationScheduleDeletePopupComponent,
  MedicationScheduleDeleteDialogComponent
} from './medication-schedule-delete-dialog.component';
import { medicationScheduleRoute, medicationSchedulePopupRoute } from './medication-schedule.route';

const ENTITY_STATES = [...medicationScheduleRoute, ...medicationSchedulePopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MedicationScheduleComponent,
    MedicationScheduleDetailComponent,
    MedicationScheduleUpdateComponent,
    MedicationScheduleDeleteDialogComponent,
    MedicationScheduleDeletePopupComponent
  ],
  entryComponents: [MedicationScheduleDeleteDialogComponent]
})
export class ElderlySmartMedicationScheduleModule {}
