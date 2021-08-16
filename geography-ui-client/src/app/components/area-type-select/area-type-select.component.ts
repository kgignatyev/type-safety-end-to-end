import {Component, Input, OnInit} from '@angular/core';
import {Area, AreaType} from '@kgi/geography-interface/geography_pb';


@Component({
  selector: 'app-area-type-select',
  templateUrl: './area-type-select.component.html'
})
export class AreaTypeSelectComponent implements OnInit {

  @Input() area: Area;

  keys: string[] = Object.keys( AreaType );
  at = AreaType;

  constructor() { }

  ngOnInit() {
    console.log( this.keys);
  }

  setAreaType(typeKey: any) {
    // @ts-ignore
    this.area.setAreaType( this.at[typeKey] );
    // tslint:disable-next-line:no-console
    console.info(typeKey);
  }

  getAreaType() {
    if ( this.area ) {
      return this.keys[this.area.getAreaType()];
    } else {
      return this.keys[0];
    }
  }
}
