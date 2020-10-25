import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {UserEffects} from './user.effects';
import {USER_FEATURE_KEY} from './user.state.interface';
import {userReducer} from "./user.reducers";

@NgModule({
  imports: [CommonModule,
    StoreModule.forFeature(USER_FEATURE_KEY, userReducer),
    EffectsModule.forFeature([UserEffects])],
  providers: [UserEffects]
})
export class UserStoreModule {
}
