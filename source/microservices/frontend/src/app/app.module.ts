import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {EmailThreadComponent} from './components/email-thread/email-thread.component';
import {EmailThreadEditViewComponent} from './views/email-thread-edit-view/email-thread-edit-view.component';
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
import {EmailIdResolver} from "./resolvers/email-id-resolver";
import {QuillModule} from 'ngx-quill';
import {EmailWriterComponent} from './components/email-writer/email-writer.component'
import {ScrollingModule} from "@angular/cdk/scrolling";
import {LandingViewComponent} from './views/landing-view/landing-view.component';
import {UnassignedViewComponent} from './views/unassigned-view/unassigned-view.component';
import {MultipleEmailThreadComponent} from './components/multiple-email-thread/multiple-email-thread.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {ReplyViewComponent} from './views/reply-view/reply-view.component';
import {EditThreadViewComponent} from './views/edit-thread-view/edit-thread-view.component';
import {EditEmailThreadComponent} from './components/edit-email-thread/edit-email-thread.component';
import {SingleEmailThreadComponent} from './components/single-email-thread/single-email-thread.component';
import {EmailThreadIdResolver} from "./resolvers/thread-id-resolver";
import {ChangeQueueViewComponent} from './views/change-queue-view/change-queue-view.component';
import {QueuePickerViewComponent} from './views/queue-picker-view/queue-picker-view.component';
import {ThreadHistoryViewComponent} from './views/thread-history-view/thread-history-view.component';
import {EmailThreadAuditComponent} from './components/email-thread-audit/email-thread-audit.component';
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {EnversRevtypePipe} from './pipes/envers-revtype.pipe';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatProgressBarModule} from "@angular/material/progress-bar";


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
    EmailThreadComponent,
    EmailThreadEditViewComponent,
    LoginComponent,
    EmailMiniatureComponent,
    EmailReaderComponent,
    EmailHeaderComponent,
    EmailReplyViewComponent,
    EmailWriterComponent,
    LandingViewComponent,
    UnassignedViewComponent,
    MultipleEmailThreadComponent,
    ReplyViewComponent,
    EditThreadViewComponent,
    EditEmailThreadComponent,
    SingleEmailThreadComponent,
    ChangeQueueViewComponent,
    QueuePickerViewComponent,
    ThreadHistoryViewComponent,
    EmailThreadAuditComponent,
    EnversRevtypePipe
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
    ScrollingModule,
    MatAutocompleteModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatProgressBarModule
  ],
  providers: [
    EmailIdResolver,
    EmailThreadIdResolver,
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

