import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {EmailthreadsComponent} from './components/emailthreads/emailthreads.component';
import {EmailthreadViewComponent} from './views/emailthread-view/emailthread-view.component';
import {EmailthreadService} from "./api/services/emailthread.service";

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
    EmailthreadService
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
