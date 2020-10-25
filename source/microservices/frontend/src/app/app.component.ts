import {Component} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {UserFacade} from "./root-store/user/user.facade";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  private service: KeycloakService;
  private facade: UserFacade;


  constructor(service: KeycloakService, facade: UserFacade) {
    this.service = service;
    this.facade = facade;
  }

  logout() {
    this.service.logout();
  }
}
