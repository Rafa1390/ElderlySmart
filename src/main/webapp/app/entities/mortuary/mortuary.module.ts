import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { MortuaryComponent } from './mortuary.component';
import { MortuaryDetailComponent } from './mortuary-detail.component';
import { MortuaryUpdateComponent } from './mortuary-update.component';
import { MortuaryDeletePopupComponent, MortuaryDeleteDialogComponent } from './mortuary-delete-dialog.component';
import { mortuaryRoute, mortuaryPopupRoute } from './mortuary.route';

const ENTITY_STATES = [...mortuaryRoute, ...mortuaryPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MortuaryComponent,
    MortuaryDetailComponent,
    MortuaryUpdateComponent,
    MortuaryDeleteDialogComponent,
    MortuaryDeletePopupComponent
  ],
  entryComponents: [MortuaryDeleteDialogComponent]
})
export class ElderlySmartMortuaryModule {}
