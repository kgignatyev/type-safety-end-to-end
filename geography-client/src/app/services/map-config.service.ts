import {Injectable} from '@angular/core';
import {LazyMapsAPILoaderConfigLiteral} from '@agm/core';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MapConfigService implements LazyMapsAPILoaderConfigLiteral {

  public apiKey: string;
  public libraries: string[];
  constructor() {
    this.apiKey = environment.googleApiKey;
    this.libraries = ['places', 'drawing', 'geometry'];
    console.log('lazy map init with ' + this.apiKey);
  }
}


