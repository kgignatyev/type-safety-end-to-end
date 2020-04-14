import {Component, Input, NgZone, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {GeographyService} from "../../services/geography.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Area, LatLng} from "@kgi/geograply-interface/geography_pb";
import {Subscription} from "rxjs";
import {LatLngLiteral, PolygonOptions} from "@agm/core/services/google-maps-types";
import {AgmMap} from "@agm/core";
import {AgmDrawingManager} from "@agm/drawing";

declare const google: any;

@Component({
  selector: 'app-area',
  templateUrl: './area.component.html'
})
export class AreaComponent implements OnInit, OnDestroy {

  area: Area;
  sub: Subscription;


  @Input() centerLat = 40.476170;
  @Input() centerLng = -100.925158;
  @Input() zoom = 4;
  map: google.maps.Map;
  agmPolyPaths: Array<LatLngLiteral>;


  @ViewChild(AgmDrawingManager, {static: true})
  agmDrawingManager: AgmDrawingManager;




  constructor(private geographySvc: GeographyService, public route: ActivatedRoute, public router: Router, private ngZone: NgZone) {
  }

  ngOnInit() {
    this.subscribeToRouteParams();
    window['angularComponentReference'] = { component: this, zone: this.ngZone, readNewPath: () => this.updatePolygonPaths(), };

  }

  newArea() {
    return new Area();
  }

  polygon: google.maps.Polygon;


  polyOptions: PolygonOptions = {
    draggable: true,
    editable: true,
    paths: [new Point(1,1),new Point(1,2),new Point(2,2),new Point(1,2),new Point(1,1)]
  }

  managerOptions = {
    drawingControl: true,
    drawingControlOptions: {
      drawingModes: ['polygon']
    },
    polygonOptions: this.polyOptions,
    drawingMode: "polygon"
  };



  options: any = {
    lat: 33.5362475,
    lng: -111.9267386,
    zoom: 10,
    fillColor: '#DC143C',
    draggable: true,
    editable: true,
    visible: true
  };

  polygonCreated($event) {

    if (this.polygon) {
      this.polygon.setMap(null);
    }
    this.polygon = $event;
    this.addPolygonChangeEvent(this.polygon);
    google.maps.event.addListener(this.polygon, 'coordinates_changed', function (index, obj) {
      // Polygon object: yourPolygon
      // debugger
      window['angularComponentReference'].zone.run(() => { window['angularComponentReference'].readNewPath(); });
      console.log('coordinates_changed:');
    });
    this.updatePolygonPaths();
  }

  updatePolygonPaths():Array<LatLng> {
    console.log("get path");
    let path:Array<LatLng> = [];
    if (this.polygon) {
      const vertices = this.polygon.getPaths().getArray()[0];

      vertices.getArray().forEach(function (xy, i) {
        const p = new LatLng();
        p.setLat(xy.lat());
        p.setLng(xy.lng());
        path.push(p);
      });
    }
    this.area.getPolygon().setVerticesList( path )
    return path
  }

  addPolygonChangeEvent(polygon) {
    var me = polygon,
      isBeingDragged = false,
      triggerCoordinatesChanged = function () {
        // Broadcast normalized event
        google.maps.event.trigger(me, 'coordinates_changed');
      };

    // If  the overlay is being dragged, set_at gets called repeatedly,
    // so either we can debounce that or igore while dragging,
    // ignoring is more efficient
    google.maps.event.addListener(me, 'dragstart', function () {
      isBeingDragged = true;
    });

    // If the overlay is dragged
    google.maps.event.addListener(me, 'dragend', function () {
      triggerCoordinatesChanged();
      isBeingDragged = false;
    });

    // Or vertices are added to any of the possible paths, or deleted
    var paths = me.getPaths();
    paths.forEach(function (path, i) {
      google.maps.event.addListener(path, "insert_at", function () {
        triggerCoordinatesChanged();
      });
      google.maps.event.addListener(path, "set_at", function () {
        if (!isBeingDragged) {
          triggerCoordinatesChanged();
        }
      });
      google.maps.event.addListener(path, "remove_at", function () {
        triggerCoordinatesChanged();
      });
    });
  };

  subscribeToRouteParams() {
    this.sub = this.route.params.subscribe(async params => {
      let areaId = params.id;
      if (areaId) {
        if ('new' == areaId) {
          this.area = this.newArea()
        } else {
          this.area = await this.geographySvc.getAreaById(areaId);
        }
      } else {
        this.area = this.newArea()
      }
      this.drawArea();
    });
  }


  close() {
    this.router.navigateByUrl('/areas');
  }

  isNewArea() {
    return this.area.getId() == '';
  }

  async save() {

    if (this.isNewArea()) {
      this.area = await this.geographySvc.createArea(this.area)
    } else {
      this.area = await this.geographySvc.updateArea(this.area)
    }
    this.close()
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  onMapReady(map: google.maps.Map): void {
    this.map = map;
    this.drawArea();
  }

  drawArea(){
    if( this.area && this.map ){
      const vertices = this.area.getPolygon().getVerticesList().map( point => new Point( point.getLat(), point.getLng() ) );
      this.agmPolyPaths = vertices
      const polygon = new google.maps.Polygon();

      polygon.setPath( vertices );
      polygon.setEditable(true);
      polygon.setMap( this.map);
      if (vertices.length > 0) {
        const newCenter = vertices[0]
        this.centerLng = newCenter.lng;
        this.centerLat = newCenter.lat;
      }
      // this.polygon = polygon
      //
      this.polygonCreated( polygon );
      // this.polygon.getPath().addListener("set_at", this.polyPathsChange )
      // this.polygon.getPath().addListener("insert_at", this.polyPathsChange )
    }
  }

  polyPathsChange($event: any) {
    console.info($event)
    console.info(this.updatePolygonPaths())
  }
}

export class Point implements LatLngLiteral {
  constructor(public lat: number, public lng: number) {
  }

}
