import { Injectable } from '@angular/core';
import createAuth0Client from '@auth0/auth0-spa-js';
import Auth0Client from '@auth0/auth0-spa-js/dist/typings/Auth0Client';
import {from, of, Observable, BehaviorSubject, combineLatest, throwError, Subscription} from 'rxjs';
import { tap, catchError, concatMap, shareReplay } from 'rxjs/operators';
import { Router } from '@angular/router';
import {Auth0Config, ConfigurationService} from "./configuration.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // Create subject and public observable of user profile data
  private userProfileSubject$ = new BehaviorSubject<any>(null);
  userProfile$ = this.userProfileSubject$.asObservable();
  // Create a local property for login status
  loggedIn: boolean = null;
  // Create an observable of Auth0 instance of client
  auth0Client$:Observable<Auth0Client> ;
  // Define observables for SDK methods that return promises by default
  // For each Auth0 SDK method, first ensure the client instance is ready
  // concatMap: Using the client instance, call SDK method; SDK returns a promise
  // from: Convert that resulting promise into an observable
  isAuthenticated$;
  handleRedirectCallback$;
  private auth0config: Auth0Config;
  tokenSubscription: Subscription;
  jwt: string;


  constructor(private router: Router, public configService:ConfigurationService) {
    this.auth0config = configService.getAuth0Config();

    this.auth0Client$ = (from(
      createAuth0Client({
        domain: this.auth0config.domain,
        client_id: this.auth0config.clientId,
        audience: this.auth0config.audience,
        redirect_uri: `${window.location.origin}`,
        scope: 'openid email profile'
      })
    ) as Observable<Auth0Client>).pipe(
      shareReplay(1), // Every subscription receives the same shared value
      catchError(err => throwError(err))
    );

    this.isAuthenticated$ = this.auth0Client$.pipe(
      concatMap((client: Auth0Client) => from(client.isAuthenticated())),
      tap(res => {
        this.loggedIn = res
        if( this.loggedIn ){

          // client.getTokenSilently(options)
        }
      })
    );
    this.handleRedirectCallback$ = this.auth0Client$.pipe(
      concatMap((client: Auth0Client) => from(client.handleRedirectCallback()))
    );

    // On initial load, check authentication state with authorization server
    // Set up local auth streams if user is already authenticated
    this.localAuthSetup();
    // Handle redirect from Auth0 login
    this.handleAuthCallback();

  }

  getTokenSilently$(options?): Observable<string> {
    return this.auth0Client$.pipe(
      concatMap((client: Auth0Client) => {
        client.getIdTokenClaims().then( r =>{
          this.jwt = r.__raw;
          console.info(r);
        })
        return from(client.getTokenSilently(options))
      }),
      tap( i => {
        // debugger;
        console.info(i)
      })
    );
  }

  // When calling, options can be passed if desired
  // https://auth0.github.io/auth0-spa-js/classes/auth0client.html#getuser
  getUser$(options?): Observable<any> {
    return this.auth0Client$.pipe(
      concatMap((client: Auth0Client) => {
          return from(client.getUser(options))
        }
      ),
      tap(user => this.userProfileSubject$.next(user))
    );
  }

  private localAuthSetup() {
    // This should only be called on app initialization
    // Set up local authentication streams
    const checkAuth$ = this.isAuthenticated$.pipe(
      concatMap((loggedIn: boolean) => {
        if (loggedIn) {
          // If authenticated, get user and set in app
          // NOTE: you could pass options here if needed
          return this.getUser$();
        }
        // If not authenticated, return stream that emits 'false'
        return of(loggedIn);
      })
    );
    checkAuth$.subscribe();
  }

  login(redirectPath: string = '/') {
    // A desired redirect path can be passed to login method
    // (e.g., from a route guard)
    // Ensure Auth0 client instance exists
    this.auth0Client$.subscribe((client: Auth0Client) => {
      // Call method to log in
      client.loginWithRedirect({
        redirect_uri: `${window.location.origin}`,
        appState: { target: redirectPath }
      });
    });
  }

  private handleAuthCallback() {
    // Call when app reloads after user logs in with Auth0
    const params = window.location.search;
    if (params.includes('code=') && params.includes('state=')) {
      let targetRoute: string; // Path to redirect to after login processsed

      const authComplete$ = this.handleRedirectCallback$.pipe(
        // Have client, now call method to handle auth callback redirect
        tap((cbRes:any) => {
          // Get and set target redirect route from callback results
          targetRoute = cbRes.appState && cbRes.appState.target ? cbRes.appState.target : '/';
        }),
        concatMap(() => {
          // Redirect callback complete; get user and login status
          return combineLatest([
            this.getUser$(),
            this.isAuthenticated$
          ]);
        })
      );

      // Subscribe to authentication completion observable
      // Response will be an array of user and login status
      authComplete$.subscribe(([user, loggedIn]) => {
        // Redirect to target route after callback processing
        this.tokenSubscription = this.getTokenSilently$().subscribe( t =>{
          console.info( t )
        } )
        this.router.navigate([targetRoute]);
      });
    }
  }

  logout() {
    // Ensure Auth0 client instance exists
    this.auth0Client$.subscribe((client: Auth0Client) => {
      // Call method to log out
      client.logout({
        client_id: "YOUR_CLIENT_ID",
        returnTo: `${window.location.origin}`
      });
    });
  }

}
