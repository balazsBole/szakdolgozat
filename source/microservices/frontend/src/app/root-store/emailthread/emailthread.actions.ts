import {createAction, props} from '@ngrx/store';
import {EmailthreadService} from "../../api/services/emailthread.service";
import {Emailthread} from "../../api/models/emailthread";


export enum ActionTypes {
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

export const searchUnassignedAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED,
  props<{ params: EmailthreadService.UnassignedParams }>());
export const searchUnassignedSuccessAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED_SUCCESS,
  props<{ searchResults: Array<Emailthread> }>());
export const searchUnassignedFailAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED_FAIL,
  props<{ error: any }>());

export const searchAssignedToMeByStatusAction = createAction(
  ActionTypes.SEARCH_ASSIGNED_TO_ME,
  props<{ status?: string }>());
export const searchAssignedToMeSuccessAction = createAction(
  ActionTypes.SEARCH_ASSIGNED_TO_ME_SUCCESS,
  props<{ searchResults: Array<Emailthread> }>());
export const searchAssignedToMeFailAction = createAction(
  ActionTypes.SEARCH_ASSIGNED_TO_ME_FAIL,
  props<{ error: any }>());

export const patchAction = createAction(
  ActionTypes.PATCH,
  props<{ params: EmailthreadService.PatchParams }>());
export const patchSuccessAction = createAction(
  ActionTypes.PATCH_SUCCESS,
  props<{ emailthread: Emailthread }>());
export const patchFailAction = createAction(
  ActionTypes.PATCH_FAIL,
  props<{ error: any }>());
