import {createReducer, on} from '@ngrx/store';
import {initialState} from "./emailthread.state.interface";
import {
  patchAction, patchFailAction, patchSuccessAction,
  searchAssignedToMeByStatusAction,
  searchAssignedToMeFailAction,
  searchAssignedToMeSuccessAction,
  searchUnassignedAction,
  searchUnassignedFailAction,
  searchUnassignedSuccessAction
} from "./emailthread.actions";


export const _emailthreadReducer = createReducer(
  initialState,

  on(searchUnassignedAction, searchAssignedToMeByStatusAction, patchAction, (state) => ({
    ...state,
    error: null,
    loading: true
  })),

  on(searchUnassignedFailAction, searchAssignedToMeFailAction, patchFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),

  on(patchSuccessAction, (state, {emailthread}) => ({
    ...state,
    patched: emailthread,
    error: null,
    loading: false
  })),

  on(searchUnassignedSuccessAction, (state, {searchResults}) => ({
    ...state,
    unassigned: searchResults,
    numberOfUnassigned: searchResults.length,
    error: null,
    loading: false
  })),

  on(searchAssignedToMeSuccessAction, (state, {searchResults}) => ({
    ...state,
    assignedToMe: searchResults,
    numberOfAssignedToMe: searchResults.length,
    error: null,
    loading: false
  })),
);

export function emailthreadReducer(state, action) {
  return _emailthreadReducer(state, action);
}
