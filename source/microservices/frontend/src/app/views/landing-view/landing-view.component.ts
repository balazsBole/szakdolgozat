import {Component, OnDestroy, OnInit} from '@angular/core';
import {from, Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-landing-view',
  templateUrl: './landing-view.component.html',
  styleUrls: ['./landing-view.component.css']
})
export class LandingViewComponent implements OnInit, OnDestroy {
  loggedIn: boolean;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly keycloakService: KeycloakService) {
  }

  ngOnInit(): void {
    from(this.keycloakService.isLoggedIn()).pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (loggedIn: boolean) => this.loggedIn = loggedIn);
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
