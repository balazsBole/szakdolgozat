import {createAction, props} from '@ngrx/store';
import {EmailThread} from "../../api/models/email-thread";
import {EmailThreadAudit} from "../../api/models/email-thread-audit";

export enum ActionTypes {
  GET_RELATED_EMAIL_THREADS = '[AUDIT] Get all the related email threads',
  GET_RELATED_EMAIL_THREADS_SUCCESS = '[AUDIT] Get all the related email threads Succes',
  GET_RELATED_EMAIL_THREADS_FAIL = '[AUDIT] Get all the related email threads Fail',

  GET_HISTORY_OF_EMAIL_THREAD = '[AUDIT] Get history of an email threads',
  GET_HISTORY_OF_EMAIL_THREAD_SUCCESS = '[AUDIT] Get history of an email threads Succes',
  GET_HISTORY_OF_EMAIL_THREAD_FAIL = '[AUDIT] Get history of an email threads Fail',

}

export const getRelatedEmailThreadsAction = createAction(
  ActionTypes.GET_RELATED_EMAIL_THREADS);
export const getRelatedEmailThreadsSuccessAction = createAction(
  ActionTypes.GET_RELATED_EMAIL_THREADS_SUCCESS,
  props<{ emailThreadRelated: EmailThread[] }>());
export const getRelatedEmailThreadsFailAction = createAction(
  ActionTypes.GET_RELATED_EMAIL_THREADS_FAIL,
  props<{ error: any }>());


export const getHistoryOfEmailThreadAction = createAction(
  ActionTypes.GET_HISTORY_OF_EMAIL_THREAD,
  props<{ emailThreadId: string }>());
export const getHistoryOfEmailThreadSuccessAction = createAction(
  ActionTypes.GET_HISTORY_OF_EMAIL_THREAD_SUCCESS,
  props<{ emailThreadHistory: EmailThreadAudit[] }>());
export const getHistoryOfEmailThreadFailAction = createAction(
  ActionTypes.GET_HISTORY_OF_EMAIL_THREAD_FAIL,
  props<{ error: any }>());
