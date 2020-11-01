import {createReducer, on} from '@ngrx/store';
import {initialState} from "./user.state.interface";
import {
  autocompleteAction,
  autocompleteFailAction,
  autocompleteSuccessAction,
  getDetailsAction,
  getDetailsFailAction,
  getDetailsSuccessAction,
  resetAction
} from "./user.actions";


export const _userReducer = createReducer(
  initialState,

  on(resetAction, () => initialState
  ),

  on(getDetailsAction, autocompleteAction, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(getDetailsFailAction, autocompleteFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),

  on(autocompleteSuccessAction, (state, {userArray}) => ({
    ...state,
    autocomplete: userArray,
    loading: false,
    error: null
  })),

  on(getDetailsSuccessAction, (state, {user}) => ({
    ...state,
    user: user,
    loading: false,
    error: null
  })),
);

export function userReducer(state, action) {
  return _userReducer(state, action);
}
