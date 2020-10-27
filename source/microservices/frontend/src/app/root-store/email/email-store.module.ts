import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {EmailEffects} from './email.effects';
import {EMAIL_FEATURE_KEY} from './email.state.interface';
import {emailReducer} from "./email.reducers";

@NgModule({
  imports: [CommonModule,
    StoreModule.forFeature(EMAIL_FEATURE_KEY, emailReducer),
    EffectsModule.forFeature([EmailEffects])],
  providers: [EmailEffects]
})
export class EmailStoreModule {
}
