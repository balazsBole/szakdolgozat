import {createAction, props} from '@ngrx/store';
import {User} from "../../api/models/user";

export enum ActionTypes {
  GET_DETAILS = '[USER] Get user details',
  GET_DETAILS_SUCCESS = '[USER] Get User details Succes',
  GET_DETAILS_FAIL = '[USER] Get User details  Fail',

  AUTOCOMPLETE = '[USER] Autocomplete user search',
  AUTOCOMPLETE_SUCCESS = '[USER] Autocomplete User search Succes',
  AUTOCOMPLETE_FAIL = '[USER] Autocomplete User search  Fail',

  RESET = '[USER] Reset User search',

}

export const getDetailsAction = createAction(
  ActionTypes.GET_DETAILS);
export const getDetailsSuccessAction = createAction(
  ActionTypes.GET_DETAILS_SUCCESS,
  props<{ user: User }>());
export const getDetailsFailAction = createAction(
  ActionTypes.GET_DETAILS_FAIL,
  props<{ error: any }>());

export const autocompleteAction = createAction(
  ActionTypes.AUTOCOMPLETE,
  props<{ username: string }>());
export const autocompleteSuccessAction = createAction(
  ActionTypes.AUTOCOMPLETE_SUCCESS,
  props<{ userArray: User[] }>());
export const autocompleteFailAction = createAction(
  ActionTypes.AUTOCOMPLETE_FAIL,
  props<{ error: any }>());

export const resetAction = createAction(
  ActionTypes.AUTOCOMPLETE_FAIL);
