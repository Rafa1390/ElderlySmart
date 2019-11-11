import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

import './vendor';
import { ElderlySmartSharedModule } from 'app/shared/shared.module';
import { ElderlySmartCoreModule } from 'app/core/core.module';
import { ElderlySmartAppRoutingModule } from './app-routing.module';
import { ElderlySmartHomeModule } from './home/home.module';
import { ElderlySmartEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ElderlySmartSharedModule,
    ElderlySmartCoreModule,
    ElderlySmartHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ElderlySmartEntityModule,
    ElderlySmartAppRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class ElderlySmartAppModule {}
