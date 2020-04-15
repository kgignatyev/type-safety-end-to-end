import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  constructor() { }

  getGeographyServiceURL():string {
    // return "http://localhost:7000"; //goproxy
    return "http://localhost:8080";//envoy
    // return "http://localhost:31019";//inside k8s
  }

  getAuth0Config(): Auth0Config {
     const r = new Auth0Config();
     r.clientId = 'AjB1PssvTOxXYDRpg3fsgFI7p7YtpziQ';
     r.domain = 'kgignatyev.auth0.com';
     r.audience = 'https://kgignatyev.auth0.com/userinfo';
     r.responseType= 'id_token'
     return r;
  }
}


export class Auth0Config {
  public domain:string
  public clientId:string
  public audience:string
  public responseType:string
}
