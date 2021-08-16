import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {AutzService} from './services/autz.service';
import {AboutComponent} from './pages/about/about.component';
import {AreasComponent} from './pages/areas/areas.component';
import {AreaComponent} from './pages/area/area.component';


const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'areas', component: AreasComponent, pathMatch: 'full', canActivate: [ AutzService ] },
  { path: 'areas/:id', component: AreaComponent, pathMatch: 'full', canActivate: [ AutzService ] },
  { path: 'about', component: AboutComponent, pathMatch: 'full', canActivate: [ AutzService ] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
