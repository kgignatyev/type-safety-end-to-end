import { Component, OnInit } from '@angular/core';
import {AutzService} from "../../services/autz.service";



@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html'
})
export class MenuComponent implements OnInit {

  constructor(   public autz: AutzService) { }

  ngOnInit() {
  }

}
