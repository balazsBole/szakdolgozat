import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmailthreadViewComponent} from "./views/emailthread-view/emailthread-view.component";
import {AuthenticationGuardService} from "./service/authentication-guard.service";

const routes: Routes = [
  {
    path: 'emailthread',
    component: EmailthreadViewComponent,
    canActivate: [AuthenticationGuardService],
    data: {roles: ['regular_user']}
  },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
