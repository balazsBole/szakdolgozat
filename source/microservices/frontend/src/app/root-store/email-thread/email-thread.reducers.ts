import {createReducer, on} from '@ngrx/store';
import {initialState} from "./email-thread.state.interface";
import {
  getDetailsAction,
  getDetailsFailAction,
  getDetailsSuccessAction,
  patchAction,
  patchFailAction,
  patchSuccessAction,
  searchAssignedToMeByStatusAction,
  searchAssignedToMeFailAction,
  searchAssignedToMeSuccessAction,
  searchUnassignedAction,
  searchUnassignedFailAction,
  searchUnassignedSuccessAction
} from "./email-thread.actions";


export const _emailThreadReducer = createReducer(
  initialState,

  on(searchUnassignedAction, searchAssignedToMeByStatusAction, patchAction, getDetailsAction, (state) => ({
    ...state,
    patched: false,
    error: null,
    loading: true
  })),

  on(searchUnassignedFailAction, searchAssignedToMeFailAction, patchFailAction, getDetailsFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),

  on(getDetailsSuccessAction, (state, {emailThread}) => ({
    ...state,
    details: emailThread,
    error: null,
    loading: false
  })),
  on(patchSuccessAction, (state) => ({
    ...state,
    patched: true,
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

export function emailThreadReducer(state, action) {
  return _emailThreadReducer(state, action);
}
