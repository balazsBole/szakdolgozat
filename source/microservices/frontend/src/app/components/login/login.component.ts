import {Component, OnDestroy, OnInit} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {UserFacade} from "../../root-store/user/user.facade";
import {takeUntil} from "rxjs/operators";
import {User} from "../../api/models/user";
import {from, Subject} from "rxjs";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  username: string;
  loggedIn: boolean = false;
  private readonly ngUnsubscribe = new Subject();
  private keycloakService: KeycloakService;
  private userFacade: UserFacade;

  constructor(service: KeycloakService, facade: UserFacade) {
    this.keycloakService = service;
    this.userFacade = facade;
  }

  logout() {
    console.log('logging out')
    this.keycloakService.logout(this.baseUrl().concat('/logged-out'));
  }

  ngOnInit(): void {
    this.userFacade.details();
    this.userFacade.user$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: User) => {
        if (result) this.username = result.username
      });
    from(this.keycloakService.isLoggedIn()).pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (loggedIn: boolean) => this.loggedIn = loggedIn);
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  login() {
    this.keycloakService.login({redirectUri: this.baseUrl()});
  }

  private baseUrl(): string {
    const parsedUrl = new URL(window.location.href);
    return parsedUrl.origin;
  }
}
