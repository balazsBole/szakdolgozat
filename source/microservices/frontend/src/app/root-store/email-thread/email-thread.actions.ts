import {createAction, props} from '@ngrx/store';
import {EmailThreadService} from "../../api/services/email-thread.service";
import {EmailThread} from "../../api/models/email-thread";
import {EmailThreadVersion} from "../../api/models/email-thread-version";


export enum ActionTypes {
  GET_DETAILS = '[EMAIL_THREAD] Get emailThread details',
  GET_DETAILS_SUCCESS = '[EMAIL_THREAD] Get emailThread details Succes',
  GET_DETAILS_FAIL = '[EMAIL_THREAD] Get emailThread details  Fail',

  SEARCH_UNASSIGNED = '[EMAIL_THREAD] Search unassigned',
  SEARCH_UNASSIGNED_SUCCESS = '[EMAIL_THREAD] Search unassigned Success',
  SEARCH_UNASSIGNED_FAIL = '[EMAIL_THREAD] Search unassigned Fail',

  SEARCH_ASSIGNED_TO_ME = '[EMAIL_THREAD] Search assigned to me',
  SEARCH_ASSIGNED_TO_ME_SUCCESS = '[EMAIL_THREAD] Search assignedToMe Success',
  SEARCH_ASSIGNED_TO_ME_FAIL = '[EMAIL_THREAD] Search assigned to me Fail',

  SEARCH_BY_STATUS_IN_ASSIGNED_QUEUE = '[EMAIL_THREAD] Search by status in assigned queue',
  SEARCH_BY_STATUS_IN_ASSIGNED_QUEUE_SUCCESS = '[EMAIL_THREAD] Search by status in assigned queue Success',
  SEARCH_BY_STATUS_IN_ASSIGNED_QUEUE_FAIL = '[EMAIL_THREAD] Search by status in assigned queue Fail',

  PATCH = '[EMAIL_THREAD] Patch values',
  PATCH_SUCCESS = '[EMAIL_THREAD] Patch values Success',
  PATCH_FAIL = '[EMAIL_THREAD] Patch values Fail',
}

export const getDetailsAction = createAction(
  ActionTypes.GET_DETAILS,
  props<{ id: string }>());
export const getDetailsSuccessAction = createAction(
  ActionTypes.GET_DETAILS_SUCCESS,
  props<{ emailThreadVersion: EmailThreadVersion }>());
export const getDetailsFailAction = createAction(
  ActionTypes.GET_DETAILS_FAIL,
  props<{ error: any }>());

export const searchUnassignedAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED);
export const searchUnassignedSuccessAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED_SUCCESS,
  props<{ searchResults: Array<EmailThread> }>());
export const searchUnassignedFailAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED_FAIL,
  props<{ error: any }>());

export const searchByStatusInAssignedQueueAction = createAction(
  ActionTypes.SEARCH_BY_STATUS_IN_ASSIGNED_QUEUE,
  props<{ status?: string }>());
export const searchByStatusInAssignedQueueSuccessAction = createAction(
  ActionTypes.SEARCH_BY_STATUS_IN_ASSIGNED_QUEUE_SUCCESS,
  props<{ searchResults: Array<EmailThread> }>());
export const searchByStatusInAssignedQueueFailAction = createAction(
  ActionTypes.SEARCH_BY_STATUS_IN_ASSIGNED_QUEUE_FAIL,
  props<{ error: any }>());

export const searchAssignedToMeByStatusAction = createAction(
  ActionTypes.SEARCH_ASSIGNED_TO_ME,
  props<{ status?: string }>());
export const searchAssignedToMeSuccessAction = createAction(
  ActionTypes.SEARCH_ASSIGNED_TO_ME_SUCCESS,
  props<{ searchResults: Array<EmailThread> }>());
export const searchAssignedToMeFailAction = createAction(
  ActionTypes.SEARCH_ASSIGNED_TO_ME_FAIL,
  props<{ error: any }>());

export const patchAction = createAction(
  ActionTypes.PATCH,
  props<{ params: EmailThreadService.PatchParams }>());
export const patchSuccessAction = createAction(
  ActionTypes.PATCH_SUCCESS);
export const patchFailAction = createAction(
  ActionTypes.PATCH_FAIL,
  props<{ error: any }>());
