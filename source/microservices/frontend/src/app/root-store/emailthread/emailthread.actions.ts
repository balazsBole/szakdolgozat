import {createAction, props} from '@ngrx/store';
import {EmailthreadService} from "../../api/services/emailthread.service";
import {Emailthread} from "../../api/models/emailthread";


export enum ActionTypes {
  SEARCH_UNASSIGNED = '[EMAILTHREAD] Search unassigned',
  SEARCH_UNASSIGNED_SUCCESS = '[EMAILTHREAD] Search Unassigned Success',
  SEARCH_UNASSIGNED_FAIL = '[EMAILTHREAD] Search Unassigned Fail',
}

export const searchUnassignedAction = createAction(
  ActionTypes.SEARCH_UNASSIGNED,
  props<{ params: EmailthreadService.UnassignedParams }>()
);
export const searchUnassignedSuccessAction = createAction(ActionTypes.SEARCH_UNASSIGNED_SUCCESS, props<{ searchResults: Array<Emailthread> }>());
export const searchUnassignedFailAction = createAction(ActionTypes.SEARCH_UNASSIGNED_FAIL, props<{ error: any }>());
