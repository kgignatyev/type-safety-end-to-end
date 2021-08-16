import { Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable()
export class AppConfigSupplier {

    authorizationService = '';
    auth0ApiAudience = '';
    auth0ClientId = '';
    auth0Domain = '';
    mode = '';
    buildNumber = '';
    authzBaseUrl = '';

    constructor(private http: HttpClient) {

    }


    public load(): Promise<any> {

        let url = '/assets/config-dev.json';
        if (environment.production) {
            url = '/assets/config.json';
        }


        const promise = this.http.get(url).toPromise().then(
            data => {
                Object.assign(this, data);
                return data;
            }
        );

        return promise;
    }
}
