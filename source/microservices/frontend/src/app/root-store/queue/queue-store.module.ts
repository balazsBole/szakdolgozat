import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {QueueEffects} from './queue.effects';
import {QUEUE_FEATURE_KEY} from "./queue.state.interface";
import {queueReducer} from "./queue.reducers";

@NgModule({
  imports: [CommonModule,
    StoreModule.forFeature(QUEUE_FEATURE_KEY, queueReducer),
    EffectsModule.forFeature([QueueEffects])],
  providers: [QueueEffects]
})
export class QueueStoreModule {
}
