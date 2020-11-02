import {createAction, props} from '@ngrx/store';
import {EmailThreadService} from "../../api/services/email-thread.service";
import {EmailThread} from "../../api/models/email-thread";


export enum ActionTypes {
  GET_DETAILS = '[EMAILTHREAD] Get emailThread details',
  GET_DETAILS_SUCCESS = '[EMAILTHREAD] Get emailThread details Succes',
  GET_DETAILS_FAIL = '[EMAILTHREAD] Get emailThread details  Fail',

  SEARCH_UNASSIGNED = '[EMAILTHREAD] Search unassigned',
  SEARCH_UNASSIGNED_SUCCESS = '[EMAILTHREAD] Search unassigned Success',
  SEARCH_UNASSIGNED_FAIL = '[EMAILTHREAD] Search unassigned Fail',

  SEARCH_ASSIGNED_TO_ME = '[EMAILTHREAD] Search assigned to me',
  SEARCH_ASSIGNED_TO_ME_SUCCESS = '[EMAILTHREAD] Search assignedToMe Success',
  SEARCH_ASSIGNED_TO_ME_FAIL = '[EMAILTHREAD] Search assigned to me Fail',

  PATCH = '[EMAILTHREAD] Patch values',
  PATCH_SUCCESS = '[EMAILTHREAD] Patch values Success',
  PATCH_FAIL = '[EMAILTHREAD] Patch values Fail',
}

export const getDetailsAction = createAction(
  ActionTypes.GET_DETAILS,
  props<{ id: string }>());
export const getDetailsSuccessAction = createAction(
  ActionTypes.GET_DETAILS_SUCCESS,
  props<{ emailThread: EmailThread }>());
export const getDetailsFailAction = createAction(
  ActionTypes.GET_DETAILS_FAIL,
  props<{ error: any }>());


export const searchUnassignedAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED,
  props<{ params: EmailThreadService.UnassignedFromTheQueueParams }>());
export const searchUnassignedSuccessAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED_SUCCESS,
  props<{ searchResults: Array<EmailThread> }>());
export const searchUnassignedFailAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED_FAIL,
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
