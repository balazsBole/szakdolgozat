import {Component, OnInit} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {UserFacade} from "./root-store/user/user.facade";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private service: KeycloakService;
  private facade: UserFacade;
  admin: boolean;

  constructor(service: KeycloakService, facade: UserFacade) {
    this.service = service;
    this.facade = facade;
  }

  ngOnInit(): void {
    this.admin = this.service.isUserInRole("admin_user");
  }

  logout() {
    this.service.logout();
  }
}
