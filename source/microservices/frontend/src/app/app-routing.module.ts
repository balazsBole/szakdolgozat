import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmailthreadViewComponent} from "./views/emailthread-view/emailthread-view.component";
import {AuthenticationGuardService} from "./service/authentication-guard.service";
import {EmailReplyViewComponent} from "./views/email-reply-view/email-reply-view.component";
import {EmailIdResolver} from "./views/email-reply-view/email-id-resolver";
import {LandingViewComponent} from "./views/landing-view/landing-view.component";

const routes: Routes = [
  {
    path: 'emailthread',
    component: EmailthreadViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']}
  },
  {
    path: 'replyTo/:uuid',
    component: EmailReplyViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']},
    resolve: {
      email: EmailIdResolver
    }
  },
  {
    path: 'landing-page',
    component: LandingViewComponent,
  },
  {
    path: '**',
    redirectTo: 'landing-page'
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
