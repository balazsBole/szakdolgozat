import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {EmailThreadStoreModule} from "./email-thread/email-thread-store.module";
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {UserStoreModule} from "./user/user-store.module";
import {EmailStoreModule} from "./email/email-store.module";
import {QueueStoreModule} from "./queue/queue-store.module";
import {AuditStoreModule} from "./audit/audit-store.module";

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
    EmailThreadStoreModule,
    UserStoreModule,
    EmailStoreModule,
    QueueStoreModule,
    AuditStoreModule
  ]
})
export class RootStoreModule {
}
