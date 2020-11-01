import {createReducer, on} from '@ngrx/store';
import {initialState} from "./queue.state.interface";
import {getQueuesAction, getQueuesFailAction, getQueuesSuccessAction} from "./queue.actions";


export const _queueReducer = createReducer(
  initialState,


  on(getQueuesAction, (state) => ({
    ...state,
    loading: true,
    error: null
  })),

  on(getQueuesFailAction, (state, {error}) => ({
    ...state,
    error,
    loading: false
  })),


  on(getQueuesSuccessAction, (state, {queueArray}) => ({
    ...state,
    queueArray: queueArray,
    loading: false,
    error: null
  })),
);

export function queueReducer(state, action) {
  return _queueReducer(state, action);
}
