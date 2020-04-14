import { Injectable } from '@angular/core';
import {ConfigurationService} from "./configuration.service";
import {GeographyClient} from "@kgi/geograply-interface/geography_pb_service";
import {Area, AreasList } from "@kgi/geograply-interface/geography_pb";
import {AutzService} from "./autz.service";
import {ServiceBase} from "./ServiceBase";

@Injectable({
  providedIn: 'root'
})
export class GeographyService extends ServiceBase {
  private geographyClient: GeographyClient;

  constructor( public configService: ConfigurationService, private authzSvc: AutzService ) {
    super();
    this.geographyClient = new GeographyClient( configService.getGeographyServiceURL())
  }

  createArea( area: Area ): Promise< Area > {
    return new Promise( ( resolve, reject ) => {
      this.geographyClient.createArea( area, this.authzSvc.grpcMetadata(), this.makeHandler( resolve, reject ) );
    } );
  }

  updateArea( area: Area ): Promise< Area > {
    return new Promise( ( resolve, reject ) => {
      this.geographyClient.updateArea( area, this.authzSvc.grpcMetadata(), this.makeHandler( resolve, reject ) );
    } );
  }

  findAreas( text: string ): Promise< AreasList > {
    return new Promise( ( resolve, reject ) => {
      this.geographyClient.findAreas( this.stringValueOf(text), this.authzSvc.grpcMetadata(), this.makeHandler( resolve, reject ) );
    } );
  }

  async getAreaById(areaId: string):  Promise< Area > {
    return new Promise( ( resolve, reject ) => {
      this.geographyClient.getAreaByID ( this.stringValueOf(areaId), this.authzSvc.grpcMetadata(), this.makeHandler( resolve, reject ) );
    } );
  }
}
