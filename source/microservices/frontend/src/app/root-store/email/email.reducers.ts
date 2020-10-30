import {createReducer, on} from '@ngrx/store';
import {initialState} from "./email.state.interface";
import {
  changeReadAction,
  changeReadFailAction,
  changeReadSuccessAction,
  getDetailsAction,
  getDetailsFailAction,
  getDetailsSuccessAction,
  sendEmailAction,
  sendEmailFailAction,
  sendEmailSuccessAction
} from "./email.actions";


export const _emailReducer = createReducer(
  initialState,

  on(getDetailsAction, sendEmailAction, changeReadAction, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(getDetailsFailAction, sendEmailFailAction, changeReadFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),

  on(changeReadSuccessAction, (state) => ({
    ...state,
    loading: false,
    error: null
  })),

  on(getDetailsSuccessAction, (state, {email}) => ({
    ...state,
    email: email,
    loading: false,
    error: null
  })),

  on(sendEmailSuccessAction, (state, {email}) => ({
    ...state,
    sentEmail: email,
    loading: false,
    error: null
  })),
);

export function emailReducer(state, action) {
  return _emailReducer(state, action);
}
