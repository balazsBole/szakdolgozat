import {createReducer, on} from '@ngrx/store';
import {initialState} from "./email.state.interface";
import {getDetailsAction, getDetailsFailAction, getDetailsSuccessAction} from "./email.actions";


export const _emailReducer = createReducer(
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

  on(getDetailsSuccessAction, (state, {email}) => ({
    ...state,
    email: email,
    loading: false,
    error: null
  })),
);

export function emailReducer(state, action) {
  return _emailReducer(state, action);
}
