import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu/menu.component';
import { AboutComponent } from './pages/about/about.component';
import { AreasComponent } from './pages/areas/areas.component';
import { AreaComponent } from './pages/area/area.component';
import { HomeComponent } from './pages/home/home.component';
import {FormsModule} from "@angular/forms";
import {AgmCoreModule, LAZY_MAPS_API_CONFIG} from "@agm/core";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {InterceptorService} from "./services/interceptor.service";
import {AgmDrawingModule} from "@agm/drawing";
import { AreaTypeSelectComponent } from './components/area-type-select/area-type-select.component';
import {MapConfigService} from "./services/map-config.service";

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    AboutComponent,
    AreasComponent,
    AreaComponent,
    HomeComponent,
    AreaTypeSelectComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    AgmCoreModule.forRoot(),
    AgmDrawingModule
  ],
  providers: [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: InterceptorService,
    multi: true
  },
    {
      provide: LAZY_MAPS_API_CONFIG,
      useClass: MapConfigService
    }

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
