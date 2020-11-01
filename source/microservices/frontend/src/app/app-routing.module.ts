import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmailThreadViewComponent} from "./views/email-thread-view/email-thread-view.component";
import {AuthenticationGuardService} from "./service/authentication-guard.service";
import {EmailReplyViewComponent} from "./views/email-reply-view/email-reply-view.component";
import {EmailIdResolver} from "./views/email-reply-view/email-id-resolver";
import {LandingViewComponent} from "./views/landing-view/landing-view.component";
import {UnassignedViewComponent} from "./views/unassigned-view/unassigned-view.component";
import {AssignThreadViewComponent} from "./views/assign-thread-view/assign-thread-view.component";
import {EmailThreadIdResolver} from "./views/assign-thread-view/thread-id-resolver";

const routes: Routes = [
  {
    path: 'email-thread',
    component: EmailThreadViewComponent,
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
    path: 'assign/:uuid',
    component: AssignThreadViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']},
    resolve: {
      email: EmailThreadIdResolver
    }
  },
  {
    path: 'unassigned',
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
