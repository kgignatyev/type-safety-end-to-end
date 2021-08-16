import { Component, OnInit } from '@angular/core';
import {AuthService} from '@auth0/auth0-angular';



@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html'
})
export class MenuComponent implements OnInit {

  constructor(public auth: AuthService) { }

  ngOnInit() {
  }

}
