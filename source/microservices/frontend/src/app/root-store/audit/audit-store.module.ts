import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {AuditEffects} from './audit.effects';
import {AUDIT_FEATURE_KEY} from "./audit.state.interface";
import {queueReducer} from "./audit.reducers";

@NgModule({
  imports: [CommonModule,
    StoreModule.forFeature(AUDIT_FEATURE_KEY, queueReducer),
    EffectsModule.forFeature([AuditEffects])],
  providers: [AuditEffects]
})
export class AuditStoreModule {
}
