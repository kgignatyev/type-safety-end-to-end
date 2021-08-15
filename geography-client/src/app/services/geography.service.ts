import { Injectable } from '@angular/core';
import {ConfigurationService} from './configuration.service';
import {AutzService} from './autz.service';
import {ServiceBase} from './ServiceBase';
import {Area, AreasList, LatLng, Polygon} from '@kgi/geography-interface/geography_pb';
import {GeographyClient} from '@kgi/geography-interface/geography_grpc_web_pb';

@Injectable({
  providedIn: 'root'
})
export class GeographyService extends ServiceBase {
  private geographyClient: GeographyClient;



  getCentroidFor( poly: Polygon ): LatLng {
    const vertices = poly.getVerticesList();
    const res = new LatLng();
    if ( vertices.length > 0 ) {
      const longitudes = vertices.map( i => i.getLng() );
      const latitudes = vertices.map( i => i.getLat() );

      latitudes.sort();
      longitudes.sort();

      const lowX = latitudes[0];
      const highX = latitudes[latitudes.length - 1];
      const lowy = longitudes[0];
      const highy = longitudes[longitudes.length - 1];

      const centerX = lowX + ( ( highX - lowX ) / 2 );
      const centerY = lowy + ( ( highy - lowy ) / 2 );


      res.setLat( centerX );
      res.setLng( centerY );
    } else {
      // center of USA
      res.setLat( 38.0 );
      res.setLng( -97.0 );
    }
    return res;
  }

  constructor( public configService: ConfigurationService, public authzSvc: AutzService ) {
    super(authzSvc);
    this.geographyClient = new GeographyClient( configService.getGeographyServiceURL());
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

  async getAreaById(areaId: string): Promise< Area > {
    return new Promise( ( resolve, reject ) => {
      this.geographyClient.getAreaByID ( this.stringValueOf(areaId), this.authzSvc.grpcMetadata(), this.makeHandler( resolve, reject ) );
    } );
  }
}
