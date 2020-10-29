import {createAction, props} from '@ngrx/store';
import {Email} from "../../api/models/email";

export enum ActionTypes {
  GET_DETAILS = '[EMAIL] Get email details',
  GET_DETAILS_SUCCESS = '[EMAIL] Get email details Succes',
  GET_DETAILS_FAIL = '[EMAIL] Get email details  Fail',

  SEND_EMAIL = '[EMAIL] Send email',
  SEND_EMAIL_SUCCESS = '[EMAIL] Send email Succes',
  SEND_EMAIL_FAIL = '[EMAIL] Send email Fail',

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


export const sendEmailAction = createAction(
  ActionTypes.SEND_EMAIL,
  props<{ email: Email }>());
export const sendEmailSuccessAction = createAction(
  ActionTypes.SEND_EMAIL_SUCCESS,
  props<{ email: Email }>());
export const sendEmailFailAction = createAction(
  ActionTypes.SEND_EMAIL_FAIL,
  props<{ error: any }>());
