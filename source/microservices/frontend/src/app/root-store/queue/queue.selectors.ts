import {createFeatureSelector, createSelector, MemoizedSelector} from '@ngrx/store';
import {QUEUE_FEATURE_KEY, QueueStoreState} from './queue.state.interface';
import {Queue} from "../../api/models";

export const selectQueueStoreState: MemoizedSelector<object, QueueStoreState> = createFeatureSelector<QueueStoreState>(QUEUE_FEATURE_KEY);

export const getError = (state: QueueStoreState): any => state.error;
export const isLoading = (state: QueueStoreState): boolean => state.loading;
export const getQueueArray = (state: QueueStoreState): Queue[] => state.queueArray;

export const selectError: MemoizedSelector<object, any> = createSelector(selectQueueStoreState, getError);
export const selectIsLoading: MemoizedSelector<object, boolean> = createSelector(selectQueueStoreState, isLoading);
export const selectQueueArray: MemoizedSelector<object, Queue[]> = createSelector(selectQueueStoreState, getQueueArray);
