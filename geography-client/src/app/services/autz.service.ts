import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {BehaviorSubject, Observable} from 'rxjs';
import {UUID} from 'angular2-uuid';
import { AuthService } from '@auth0/auth0-angular';
import * as grpc from 'grpc-web';
import grpcWeb from 'grpc-web';
import { environment as env } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AutzService implements CanActivate {

  public user$ = new BehaviorSubject<any>( null);
  public token$ = new BehaviorSubject<any>( null);
  public isAuthenticated = false;

  constructor( public authService:AuthService, private router: Router) {
    authService.user$.subscribe( u => this.user$.next( u ));
    authService.idTokenClaims$.subscribe( t => {
      // tslint:disable-next-line:no-console
      console.info('token:' + JSON.stringify( t ));
      this.token$.next( t );
    });
    authService.isAuthenticated$.subscribe( v => this.isAuthenticated = v);

  }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.token$.getValue() || route.pathFromRoot.length==0 ){
      return this.pathAllowed( route );
    }  else {
      return this.router.parseUrl('/login-required');
    }
  }


  grpcMetadata(): grpc.Metadata {
    const m = {} as grpcWeb.Metadata;
    // debugger
    m.Authorization = 'Bearer ' + this.token$.value?.__raw;
    let uuid = UUID.UUID();
    uuid = uuid.replace( /[-]/g, '' );
    m.cid = uuid;
    return m;
  }

  private pathAllowed(route: ActivatedRouteSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree{
    // debugger
    // console.info( route)
    const routePath = route.url.map( segment => segment.path ).join('/');
    // example of path requiring extra authorization
    if ( routePath === 'config'  ) {
      return this.router.parseUrl('/authorization-required?requestedAccessTo=' + routePath);
    }
    return true;
  }

  login() {
    this.authService.loginWithRedirect();
  }

  logout() {
    this.authService.logout({
      returnTo: env.appUri
    });
  }
}
