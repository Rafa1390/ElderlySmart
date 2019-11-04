import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { FuneralHistoryComponent } from './funeral-history.component';
import { FuneralHistoryDetailComponent } from './funeral-history-detail.component';
import { FuneralHistoryUpdateComponent } from './funeral-history-update.component';
import { FuneralHistoryDeletePopupComponent, FuneralHistoryDeleteDialogComponent } from './funeral-history-delete-dialog.component';
import { funeralHistoryRoute, funeralHistoryPopupRoute } from './funeral-history.route';

const ENTITY_STATES = [...funeralHistoryRoute, ...funeralHistoryPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FuneralHistoryComponent,
    FuneralHistoryDetailComponent,
    FuneralHistoryUpdateComponent,
    FuneralHistoryDeleteDialogComponent,
    FuneralHistoryDeletePopupComponent
  ],
  entryComponents: [FuneralHistoryDeleteDialogComponent]
})
export class ElderlySmartFuneralHistoryModule {}
