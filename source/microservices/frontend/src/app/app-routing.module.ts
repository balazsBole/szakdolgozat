import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmailThreadEditViewComponent} from "./views/email-thread-edit-view/email-thread-edit-view.component";
import {AuthenticationGuardService} from "./service/authentication-guard.service";
import {EmailReplyViewComponent} from "./views/email-reply-view/email-reply-view.component";
import {EmailIdResolver} from "./views/email-reply-view/email-id-resolver";
import {LandingViewComponent} from "./views/landing-view/landing-view.component";
import {UnassignedViewComponent} from "./views/unassigned-view/unassigned-view.component";
import {AssignThreadViewComponent} from "./views/assign-thread-view/assign-thread-view.component";
import {EmailThreadIdResolver} from "./views/assign-thread-view/thread-id-resolver";
import {ReplyViewComponent} from "./views/reply-view/reply-view.component";

const routes: Routes = [
  {
    path: 'email/reply',
    component: ReplyViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']}
  },
  {
    path: 'email-thread/edit',
    component: EmailThreadEditViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']}
  },
  {
    path: 'email/replyTo/:uuid',
    component: EmailReplyViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']},
    resolve: {
      email: EmailIdResolver
    }
  },
  {
    path: 'email-thread/assign/:uuid',
    component: AssignThreadViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']},
    resolve: {
      email: EmailThreadIdResolver
    }
  },
  {
    path: 'email-thread/unassigned',
    component: UnassignedViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']}
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
