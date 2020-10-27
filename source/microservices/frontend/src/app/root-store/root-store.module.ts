import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {EmailthreadStoreModule} from "./emailthread/emailthread-store.module";
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {UserStoreModule} from "./user/user-store.module";
import {EmailStoreModule} from "./email/email-store.module";

@NgModule({
  imports: [
    CommonModule,
    StoreModule.forRoot(
      {},
      {
        runtimeChecks: {
          strictStateImmutability: true,
          strictActionImmutability: true
        }
      }
    ),
    StoreDevtoolsModule.instrument({}),
    EffectsModule.forRoot([]),
    EmailthreadStoreModule,
    UserStoreModule,
    EmailStoreModule
  ]
})
export class RootStoreModule {
}
