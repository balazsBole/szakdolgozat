import {Component, OnDestroy, OnInit} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {UserFacade} from "../../root-store/user/user.facade";
import {takeUntil} from "rxjs/operators";
import {User} from "../../api/models/user";
import {from, Subject} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import * as url from "url";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  username: string;
  loggedIn: boolean = false;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly keycloakService: KeycloakService, private readonly userFacade: UserFacade, private readonly snackBar: MatSnackBar) {
  }

  logout() {
    this.keycloakService.logout(this.baseUrl());
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

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  login() {
    this.keycloakService.login({redirectUri: this.currentUrl()});
  }

  private baseUrl(): string {
    return this.currentUrl().origin;
  }

  private currentUrl(): url {
    return new URL(window.location.href);
  }
}
