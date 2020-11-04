import {createReducer, on} from '@ngrx/store';
import {initialState} from "./audit.state.interface";
import {
  getHistoryOfEmailThreadAction,
  getHistoryOfEmailThreadFailAction,
  getHistoryOfEmailThreadSuccessAction,
  getRelatedEmailThreadsAction,
  getRelatedEmailThreadsFailAction,
  getRelatedEmailThreadsSuccessAction
} from "./audit.actions";


export const _queueReducer = createReducer(
  initialState,


  on(getRelatedEmailThreadsAction, getHistoryOfEmailThreadAction, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(getRelatedEmailThreadsFailAction, getHistoryOfEmailThreadFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),

  on(getHistoryOfEmailThreadSuccessAction, (state, {emailThreadHistory}) => ({
    ...state,
    emailThreadHistory: emailThreadHistory,
    loading: false,
    error: null
  })),

  on(getRelatedEmailThreadsSuccessAction, (state, {emailThreadRelated}) => ({
    ...state,
    emailThreadRelated: emailThreadRelated,
    loading: false,
    error: null
  })),
);

export function queueReducer(state, action) {
  return _queueReducer(state, action);
}
