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
import {AgmCoreModule} from "@agm/core";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {InterceptorService} from "./services/interceptor.service";
import {AgmDrawingModule} from "@agm/drawing";
import { AreaTypeSelectComponent } from './components/area-type-select/area-type-select.component';

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
    AgmCoreModule.forRoot({
      apiKey: '==use your google api key==',//restricted, todo: replace with lazy init
      libraries: ['places', 'drawing', 'geometry'],
    }),
    AgmDrawingModule
  ],
  providers: [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: InterceptorService,
    multi: true
  }

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
