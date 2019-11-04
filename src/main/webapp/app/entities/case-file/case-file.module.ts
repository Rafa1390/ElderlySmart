import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { CaseFileComponent } from './case-file.component';
import { CaseFileDetailComponent } from './case-file-detail.component';
import { CaseFileUpdateComponent } from './case-file-update.component';
import { CaseFileDeletePopupComponent, CaseFileDeleteDialogComponent } from './case-file-delete-dialog.component';
import { caseFileRoute, caseFilePopupRoute } from './case-file.route';

const ENTITY_STATES = [...caseFileRoute, ...caseFilePopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CaseFileComponent,
    CaseFileDetailComponent,
    CaseFileUpdateComponent,
    CaseFileDeleteDialogComponent,
    CaseFileDeletePopupComponent
  ],
  entryComponents: [CaseFileDeleteDialogComponent]
})
export class ElderlySmartCaseFileModule {}
