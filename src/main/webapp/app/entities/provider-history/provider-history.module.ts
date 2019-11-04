import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { ProviderHistoryComponent } from './provider-history.component';
import { ProviderHistoryDetailComponent } from './provider-history-detail.component';
import { ProviderHistoryUpdateComponent } from './provider-history-update.component';
import { ProviderHistoryDeletePopupComponent, ProviderHistoryDeleteDialogComponent } from './provider-history-delete-dialog.component';
import { providerHistoryRoute, providerHistoryPopupRoute } from './provider-history.route';

const ENTITY_STATES = [...providerHistoryRoute, ...providerHistoryPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProviderHistoryComponent,
    ProviderHistoryDetailComponent,
    ProviderHistoryUpdateComponent,
    ProviderHistoryDeleteDialogComponent,
    ProviderHistoryDeletePopupComponent
  ],
  entryComponents: [ProviderHistoryDeleteDialogComponent]
})
export class ElderlySmartProviderHistoryModule {}
