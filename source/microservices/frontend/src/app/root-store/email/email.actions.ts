import {createAction, props} from '@ngrx/store';
import {Email} from "../../api/models/email";

export enum ActionTypes {
  GET_DETAILS = '[EMAIL] Get email details',
  GET_DETAILS_SUCCESS = '[EMAIL] Get email details Succes',
  GET_DETAILS_FAIL = '[EMAIL] Get email details  Fail',

}

export const getDetailsAction = createAction(
  ActionTypes.GET_DETAILS,
  props<{ id: string }>());
export const getDetailsSuccessAction = createAction(
  ActionTypes.GET_DETAILS_SUCCESS,
  props<{ email: Email }>());
export const getDetailsFailAction = createAction(
  ActionTypes.GET_DETAILS_FAIL,
  props<{ error: any }>());
