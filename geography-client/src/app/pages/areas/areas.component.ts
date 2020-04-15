import { Component, OnInit } from '@angular/core';
import {GeographyService} from "../../services/geography.service";
import {Area} from "@kgi/geograply-interface/geography_pb";

@Component({
  selector: 'app-areas',
  templateUrl: './areas.component.html'
})
export class AreasComponent implements OnInit {

  areas: Array<Area>;

  constructor( private geographySvc: GeographyService) { }

  async ngOnInit() {
    try {
      const areas = await this.geographySvc.findAreas('')
      this.areas = areas.getItemsList();
    }catch (e) {
      this.areas = [];
    }

  }

}
