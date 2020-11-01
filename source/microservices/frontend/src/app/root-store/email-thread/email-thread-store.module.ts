import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {EmailThreadEffects} from './email-thread.effects';
import {EMAILTHREAD_FEATURE_KEY} from './email-thread.state.interface';
import {emailThreadReducer} from "./email-thread.reducers";

@NgModule({
  imports: [CommonModule, StoreModule.forFeature(EMAILTHREAD_FEATURE_KEY, emailThreadReducer), EffectsModule.forFeature([EmailThreadEffects])],
  providers: [EmailThreadEffects]
})
export class EmailThreadStoreModule {
}
