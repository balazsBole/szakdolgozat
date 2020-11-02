import {Component, OnInit} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {UserFacade} from "./root-store/user/user.facade";
import {Observable} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  admin: boolean;
  hasQueue$: Observable<boolean>;

  constructor(private readonly service: KeycloakService, private readonly userFacade: UserFacade) {
  }

  ngOnInit(): void {
    this.admin = this.service.isUserInRole("admin_user");
    this.hasQueue$ = this.userFacade.hasQueue();
  }

  logout() {
    this.service.logout();
  }
}
