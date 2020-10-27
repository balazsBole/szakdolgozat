import {createReducer, on} from '@ngrx/store';
import {initialState} from "./user.state.interface";
import {getDetailsAction, getDetailsFailAction, getDetailsSuccessAction} from "./user.actions";


export const _userReducer = createReducer(
  initialState,

  on(getDetailsAction, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(getDetailsFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
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
