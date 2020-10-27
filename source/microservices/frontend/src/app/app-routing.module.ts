import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmailthreadViewComponent} from "./views/emailthread-view/emailthread-view.component";
import {AuthenticationGuardService} from "./service/authentication-guard.service";
import {LoggedOutViewComponent} from "./views/logged-out-view/logged-out-view.component";
import {EmailReplyViewComponent} from "./views/email-reply-view/email-reply-view.component";
import {EmailIdResolver} from "./views/email-reply-view/email-id-resolver";

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
        path: 'logged-out',
        component: LoggedOutViewComponent,
    },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
