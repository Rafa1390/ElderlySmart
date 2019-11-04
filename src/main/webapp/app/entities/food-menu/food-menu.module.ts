import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { FoodMenuComponent } from './food-menu.component';
import { FoodMenuDetailComponent } from './food-menu-detail.component';
import { FoodMenuUpdateComponent } from './food-menu-update.component';
import { FoodMenuDeletePopupComponent, FoodMenuDeleteDialogComponent } from './food-menu-delete-dialog.component';
import { foodMenuRoute, foodMenuPopupRoute } from './food-menu.route';

const ENTITY_STATES = [...foodMenuRoute, ...foodMenuPopupRoute];

@NgModule({
  imports: [ElderlySmartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FoodMenuComponent,
    FoodMenuDetailComponent,
    FoodMenuUpdateComponent,
    FoodMenuDeleteDialogComponent,
    FoodMenuDeletePopupComponent
  ],
  entryComponents: [FoodMenuDeleteDialogComponent]
})
export class ElderlySmartFoodMenuModule {}
