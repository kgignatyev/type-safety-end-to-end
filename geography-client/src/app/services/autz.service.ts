import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {grpc} from "@improbable-eng/grpc-web";
import {UUID} from "angular2-uuid";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class AutzService implements CanActivate{

  constructor( public authService:AuthService) { }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return true;
  }


  grpcMetadata(): grpc.Metadata {
    const meta = new grpc.Metadata();
    meta.set( 'Authorization', 'Bearer ' + this.authService.jwt );
    let uuid = UUID.UUID();
    uuid = uuid.replace( /[-]/g, '' );
    meta.set( 'cid', uuid );
    return meta;
  }
}
