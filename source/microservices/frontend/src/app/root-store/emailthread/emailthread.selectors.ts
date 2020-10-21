import {createFeatureSelector, createSelector, MemoizedSelector} from '@ngrx/store';
import {EMAILTHREAD_FEATURE_KEY, EmailthreadStoreState} from './emailthread.state.interface';
import {Emailthread} from "../../api/models/emailthread";

export const selectEmailthreadStoreState: MemoizedSelector<object, EmailthreadStoreState> = createFeatureSelector<EmailthreadStoreState>(EMAILTHREAD_FEATURE_KEY);

export const getError = (state: EmailthreadStoreState): any => state.error;
export const isLoading = (state: EmailthreadStoreState): boolean => state.loading;
export const getEmailthreads = (state: EmailthreadStoreState): Emailthread[] => state.emailthreads;
export const getTotalCount = (state: EmailthreadStoreState): number => state.totalCount;

export const selectEmailthreadError: MemoizedSelector<object, any> = createSelector(selectEmailthreadStoreState, getError);
export const selectEmailthreadIsLoading: MemoizedSelector<object, boolean> = createSelector(selectEmailthreadStoreState, isLoading);
export const selectEmailthreads: MemoizedSelector<object, Emailthread[]> = createSelector(selectEmailthreadStoreState, getEmailthreads);
export const selectEmailthreadsTotalCount: MemoizedSelector<object, number> = createSelector(selectEmailthreadStoreState, getTotalCount);
