import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmailthreadViewComponent} from "./views/emailthread-view/emailthread-view.component";
import {AuthenticationGuardService} from "./service/authentication-guard.service";
import {LoggedOutViewComponent} from "./views/logged-out-view/logged-out-view.component";

const routes: Routes = [
  {
    path: 'emailthread',
    component: EmailthreadViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']}
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
