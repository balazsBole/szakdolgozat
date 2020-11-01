import {createAction, props} from '@ngrx/store';
import {Queue} from "../../api/models/queue";

export enum ActionTypes {
  GET_QUEUES = '[QUEUE] Get all the queues',
  GET_QUEUES_SUCCESS = '[QUEUE] Get all the queues Succes',
  GET_QUEUES_FAIL = '[QUEUE] Get all the queues Fail',

}

export const getQueuesAction = createAction(
  ActionTypes.GET_QUEUES);
export const getQueuesSuccessAction = createAction(
  ActionTypes.GET_QUEUES_SUCCESS,
  props<{ queueArray: Queue[] }>());
export const getQueuesFailAction = createAction(
  ActionTypes.GET_QUEUES_FAIL,
  props<{ error: any }>());
