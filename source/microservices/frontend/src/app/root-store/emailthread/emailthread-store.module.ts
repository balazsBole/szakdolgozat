import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {EmailthreadEffects} from './emailthread.effects';
import {EMAILTHREAD_FEATURE_KEY} from './emailthread.state.interface';
import {emailthreadReducer} from "./emailthread.reducers";

@NgModule({
  imports: [CommonModule, StoreModule.forFeature(EMAILTHREAD_FEATURE_KEY, emailthreadReducer), EffectsModule.forFeature([EmailthreadEffects])],
  providers: [EmailthreadEffects]
})
export class EmailthreadStoreModule {
}
