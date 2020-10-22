import {createReducer, on} from '@ngrx/store';
import {initialState} from "./emailthread.state.interface";
import {
  searchAssignedToMeByStatusAction,
  searchAssignedToMeFailAction,
  searchAssignedToMeSuccessAction,
  searchUnassignedAction,
  searchUnassignedFailAction,
  searchUnassignedSuccessAction
} from "./emailthread.actions";


export const _emailthreadReducer = createReducer(
  initialState,

  on(searchUnassignedAction, searchAssignedToMeByStatusAction, (state) => ({
    ...state,
    loading: true
  })),

  on(searchUnassignedFailAction, searchAssignedToMeFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),

  on(searchUnassignedSuccessAction, (state, {searchResults}) => ({
    ...state,
    unassigned: searchResults,
    numberOfUnassigned: searchResults.length,
    loading: false
  })),

  on(searchAssignedToMeSuccessAction, (state, {searchResults}) => ({
    ...state,
    assignedToMe: searchResults,
    numberOfAssignedToMe: searchResults.length,
    loading: false
  })),
);

export function emailthreadReducer(state, action) {
  return _emailthreadReducer(state, action);
}
