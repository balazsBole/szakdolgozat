import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {EmailthreadComponent} from './components/emailthread/emailthread.component';
import {EmailthreadViewComponent} from './views/emailthread-view/emailthread-view.component';
import {HelpdeskBackendModule} from "./api/helpdesk-backend.module";
import {RootStoreModule} from "./root-store/root-store.module";
import {MatDividerModule} from "@angular/material/divider";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {LoginComponent} from './components/login/login.component';
import {LoggedOutViewComponent} from './views/logged-out-view/logged-out-view.component';
import {EmailMiniatureComponent} from './components/email-miniature/email-miniature.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MatNativeDateModule, MatOptionModule} from "@angular/material/core";
import {MatTreeModule} from "@angular/material/tree";
import {MatSelectModule} from "@angular/material/select";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatGridListModule} from "@angular/material/grid-list";
import {EmailHeaderComponent} from './components/email-header/email-header.component';
import {EmailReaderComponent} from "./components/email-reader/email-reader.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTooltipModule} from "@angular/material/tooltip";
import {EmailReplyViewComponent} from './views/email-reply-view/email-reply-view.component';
import {EmailIdResolver} from "./views/email-reply-view/email-id-resolver";
import {QuillModule} from 'ngx-quill';
import {EmailWriterComponent} from './components/email-writer/email-writer.component'
import {ScrollingModule} from "@angular/cdk/scrolling";


function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8082/auth',
        realm: 'helpdesk',
        clientId: 'helpdesk-frontend',
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
    });
}

@NgModule({
  declarations: [
    AppComponent,
    EmailthreadComponent,
    EmailthreadViewComponent,
    LoginComponent,
    LoggedOutViewComponent,
    EmailMiniatureComponent,
    EmailReaderComponent,
    EmailHeaderComponent,
    EmailReplyViewComponent,
    EmailWriterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HelpdeskBackendModule,
    RootStoreModule,
    MatDividerModule,
    MatCardModule,
    KeycloakAngularModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    HttpClientModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatTreeModule,
    MatOptionModule,
    MatSelectModule,
    MatSnackBarModule,
    MatGridListModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    QuillModule.forRoot(),
    FormsModule,
    ScrollingModule
  ],
  providers: [
    EmailIdResolver,
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

