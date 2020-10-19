import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmailthreadViewComponent} from "./views/emailthread-view/emailthread-view.component";

const routes: Routes = [
  {path: '', component: EmailthreadViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
