import {createReducer, on} from '@ngrx/store';
import {initialState} from "./emailthread.state.interface";
import {searchUnassignedAction, searchUnassignedFailAction, searchUnassignedSuccessAction} from "./emailthread.actions";


export const _emailthreadReducer = createReducer(
  initialState,

  on(searchUnassignedAction, (state) => ({
    ...state,
    loading: true
  })),

  on(searchUnassignedSuccessAction, (state, {searchResults}) => ({
    ...state,
    emailthreads: searchResults,
    totalCount: searchResults.length,
    loading: false
  })),

  on(searchUnassignedFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),
);

/* eslint-disable-next-line prefer-arrow/prefer-arrow-functions */
export function emailthreadReducer(state, action) {
  return _emailthreadReducer(state, action);
}
