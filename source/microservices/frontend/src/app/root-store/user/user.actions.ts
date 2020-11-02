import {createAction, props} from '@ngrx/store';
import {User} from "../../api/models/user";
import {UserService} from "../../api/services/user.service";

export enum ActionTypes {
  GET_DETAILS = '[USER] Get user details',
  GET_DETAILS_SUCCESS = '[USER] Get User details Succes',
  GET_DETAILS_FAIL = '[USER] Get User details  Fail',

  AUTOCOMPLETE = '[USER] Autocomplete user search',
  AUTOCOMPLETE_SUCCESS = '[USER] Autocomplete User search Succes',
  AUTOCOMPLETE_FAIL = '[USER] Autocomplete User search  Fail',

  CHANGE_QUEUE = '[USER] change queue',
  CHANGE_QUEUE_SUCCESS = '[USER]  change queue Succes',
  CHANGE_QUEUE_FAIL = '[USER]  change queue Fail',

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
  props<{ params: UserService.AutocompleteParams }>());
export const autocompleteSuccessAction = createAction(
  ActionTypes.AUTOCOMPLETE_SUCCESS,
  props<{ userArray: User[] }>());
export const autocompleteFailAction = createAction(
  ActionTypes.AUTOCOMPLETE_FAIL,
  props<{ error: any }>());

export const changeQueueAction = createAction(
  ActionTypes.CHANGE_QUEUE,
  props<{ body: { [key: string]: string } }>());
export const changeQueueSuccessAction = createAction(
  ActionTypes.CHANGE_QUEUE_SUCCESS);
export const changeQueueFailAction = createAction(
  ActionTypes.CHANGE_QUEUE_FAIL,
  props<{ error: any }>());

export const resetAction = createAction(
  ActionTypes.RESET);
