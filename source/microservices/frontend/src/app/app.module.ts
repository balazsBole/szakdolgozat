import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {EmailthreadsComponent} from './components/emailthreads/emailthreads.component';
import {EmailthreadViewComponent} from './views/emailthread-view/emailthread-view.component';
import {HelpdeskBackendModule} from "./api/helpdesk-backend.module";
import {RootStoreModule} from "./root-store/root-store.module";
import {MatDividerModule} from "@angular/material/divider";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";


function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8082/auth',
        realm: 'helpdesk',
        clientId: 'helpdesk-frontend',
      },
      // initOptions: {
      //   onLoad: 'login-required',
      // },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
      // enableBearerInterceptor: true,
    });
}

@NgModule({
  declarations: [
    AppComponent,
    EmailthreadsComponent,
    EmailthreadViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HelpdeskBackendModule,
    RootStoreModule,
    MatDividerModule,
    KeycloakAngularModule,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

