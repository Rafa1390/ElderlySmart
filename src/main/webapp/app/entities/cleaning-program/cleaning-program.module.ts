import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { CleaningProgramComponent } from './cleaning-program.component';
import { CleaningProgramDetailComponent } from './cleaning-program-detail.component';
import { CleaningProgramUpdateComponent } from './cleaning-program-update.component';
import { CleaningProgramDeletePopupComponent, CleaningProgramDeleteDialogComponent } from './cleaning-program-delete-dialog.component';
import { cleaningProgramRoute, cleaningProgramPopupRoute } from './cleaning-program.route';

const ENTITY_STATES = [...cleaningProgramRoute, ...cleaningProgramPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CleaningProgramComponent,
    CleaningProgramDetailComponent,
    CleaningProgramUpdateComponent,
    CleaningProgramDeleteDialogComponent,
    CleaningProgramDeletePopupComponent
  ],
  entryComponents: [CleaningProgramDeleteDialogComponent]
})
export class ElderlySmartCleaningProgramModule {}
