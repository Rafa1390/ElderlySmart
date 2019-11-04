import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { ProviderComponent } from './provider.component';
import { ProviderDetailComponent } from './provider-detail.component';
import { ProviderUpdateComponent } from './provider-update.component';
import { ProviderDeletePopupComponent, ProviderDeleteDialogComponent } from './provider-delete-dialog.component';
import { providerRoute, providerPopupRoute } from './provider.route';

const ENTITY_STATES = [...providerRoute, ...providerPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProviderComponent,
    ProviderDetailComponent,
    ProviderUpdateComponent,
    ProviderDeleteDialogComponent,
    ProviderDeletePopupComponent
  ],
  entryComponents: [ProviderDeleteDialogComponent]
})
export class ElderlySmartProviderModule {}
