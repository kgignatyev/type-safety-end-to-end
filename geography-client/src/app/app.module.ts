import { BrowserModule } from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu/menu.component';
import { AboutComponent } from './pages/about/about.component';
import { AreasComponent } from './pages/areas/areas.component';
import { AreaComponent } from './pages/area/area.component';
import { HomeComponent } from './pages/home/home.component';
import {FormsModule} from '@angular/forms';
import {AgmCoreModule, LAZY_MAPS_API_CONFIG} from '@agm/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AgmDrawingModule} from '@agm/drawing';
import { AreaTypeSelectComponent } from './components/area-type-select/area-type-select.component';
import {MapConfigService} from './services/map-config.service';
import {AuthHttpInterceptor, AuthModule} from '@auth0/auth0-angular';
import {AppConfigSupplier} from './services/app.config_supplier';
import { environment as env } from '../environments/environment';

export function appInit(appConfigService: AppConfigSupplier) {
  return () => appConfigService.load();
}

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
    HttpClientModule,
    AppRoutingModule,
    AgmCoreModule.forRoot(),
    AgmDrawingModule,
    AuthModule.forRoot({
      ...env.auth,
      httpInterceptor: {
        ...env.httpInterceptor,
      },
    })
  ],
  providers: [AppConfigSupplier,
    {
      provide: APP_INITIALIZER,
      useFactory: appInit,
      multi: true,
      deps: [AppConfigSupplier]
    }, {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHttpInterceptor,
      multi: true,
    }, {
      provide: LAZY_MAPS_API_CONFIG,
      useClass: MapConfigService
    }],

  bootstrap: [AppComponent]
})
export class AppModule { }
