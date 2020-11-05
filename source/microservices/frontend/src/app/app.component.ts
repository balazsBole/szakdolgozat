import {Component, OnDestroy, OnInit} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {UserFacade} from "./root-store/user/user.facade";
import {from, Subject} from "rxjs";
import {filter, takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  hasQueue: boolean;
  admin: boolean;
  loggedIn: boolean;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly service: KeycloakService, private readonly userFacade: UserFacade) {
  }

  ngOnInit(): void {
    from(this.service.isLoggedIn()).pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (loggedIn: boolean) => {
        this.loggedIn = loggedIn;
        this.admin = this.service.isUserInRole("admin_user");
        if (loggedIn)
          this.userFacade.details();
      });
    this.userFacade.hasQueue().pipe(filter(b => b), takeUntil(this.ngUnsubscribe)).subscribe(
      (hasQueue: boolean) => {
        this.hasQueue = hasQueue;
      });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  logout() {
    this.service.logout();
  }
}
