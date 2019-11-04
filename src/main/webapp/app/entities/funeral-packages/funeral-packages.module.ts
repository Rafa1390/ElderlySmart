import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { FuneralPackagesComponent } from './funeral-packages.component';
import { FuneralPackagesDetailComponent } from './funeral-packages-detail.component';
import { FuneralPackagesUpdateComponent } from './funeral-packages-update.component';
import { FuneralPackagesDeletePopupComponent, FuneralPackagesDeleteDialogComponent } from './funeral-packages-delete-dialog.component';
import { funeralPackagesRoute, funeralPackagesPopupRoute } from './funeral-packages.route';

const ENTITY_STATES = [...funeralPackagesRoute, ...funeralPackagesPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FuneralPackagesComponent,
    FuneralPackagesDetailComponent,
    FuneralPackagesUpdateComponent,
    FuneralPackagesDeleteDialogComponent,
    FuneralPackagesDeletePopupComponent
  ],
  entryComponents: [FuneralPackagesDeleteDialogComponent]
})
export class ElderlySmartFuneralPackagesModule {}
