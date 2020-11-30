import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmailThreadEditViewComponent} from "./views/email-thread-edit-view/email-thread-edit-view.component";
import {AuthenticationGuardService} from "./service/authentication-guard.service";
import {EmailReplyViewComponent} from "./views/email-reply-view/email-reply-view.component";
import {EmailIdResolver} from "./resolvers/email-id-resolver";
import {LandingViewComponent} from "./views/landing-view/landing-view.component";
import {UnassignedViewComponent} from "./views/unassigned-view/unassigned-view.component";
import {EmailThreadIdResolver} from "./resolvers/thread-id-resolver";
import {ReplyViewComponent} from "./views/reply-view/reply-view.component";
import {EditThreadViewComponent} from "./views/edit-thread-view/edit-thread-view.component";
import {ChangeQueueViewComponent} from "./views/change-queue-view/change-queue-view.component";
import {QueuePickerViewComponent} from "./views/queue-picker-view/queue-picker-view.component";
import {ThreadHistoryViewComponent} from "./views/thread-history-view/thread-history-view.component";

const routes: Routes = [
  {
    path: 'choose-queue',
    component: QueuePickerViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']}
  },
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
        path: 'email-thread/history',
        component: ThreadHistoryViewComponent,
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
    path: 'email-thread/edit/:uuid',
    component: EditThreadViewComponent,
    canActivate: [AuthenticationGuardService],
      data: {roles: ['regular_user']},
      resolve: {
          emailThread: EmailThreadIdResolver
      }
  },
    {
        path: 'email-thread/unassigned',
        component: UnassignedViewComponent,
        canActivate: [AuthenticationGuardService],
        data: {roles: ['admin_user']}
    },
    {
        path: 'email-thread/change-queue',
        component: ChangeQueueViewComponent,
        canActivate: [AuthenticationGuardService],
        data: {roles: ['admin_user']}
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
