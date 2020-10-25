import {createAction, props} from '@ngrx/store';
import {User} from "../../api/models/user";

export enum ActionTypes {
  GET_DETAILS = '[USER] Get user details',
  GET_DETAILS_SUCCESS = '[USER] Get User details Succes',
  GET_DETAILS_FAIL = '[USER] Get User details  Fail',

}

export const getDetailsAction = createAction(
  ActionTypes.GET_DETAILS);
export const getDetailsSuccessAction = createAction(
  ActionTypes.GET_DETAILS_SUCCESS,
  props<{ user: User }>());
export const getDetailsFailAction = createAction(
  ActionTypes.GET_DETAILS_FAIL,
  props<{ error: any }>());
