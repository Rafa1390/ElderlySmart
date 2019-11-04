import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { MedicamentComponent } from './medicament.component';
import { MedicamentDetailComponent } from './medicament-detail.component';
import { MedicamentUpdateComponent } from './medicament-update.component';
import { MedicamentDeletePopupComponent, MedicamentDeleteDialogComponent } from './medicament-delete-dialog.component';
import { medicamentRoute, medicamentPopupRoute } from './medicament.route';

const ENTITY_STATES = [...medicamentRoute, ...medicamentPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MedicamentComponent,
    MedicamentDetailComponent,
    MedicamentUpdateComponent,
    MedicamentDeleteDialogComponent,
    MedicamentDeletePopupComponent
  ],
  entryComponents: [MedicamentDeleteDialogComponent]
})
export class ElderlySmartMedicamentModule {}
