import {createFeatureSelector, createSelector, MemoizedSelector} from '@ngrx/store';
import {EMAILTHREAD_FEATURE_KEY, EmailthreadStoreState} from './emailthread.state.interface';
import {Emailthread} from "../../api/models/emailthread";

export const selectEmailthreadStoreState: MemoizedSelector<object, EmailthreadStoreState> = createFeatureSelector<EmailthreadStoreState>(EMAILTHREAD_FEATURE_KEY);

export const getError = (state: EmailthreadStoreState): any => state.error;
export const isLoading = (state: EmailthreadStoreState): boolean => state.loading;
export const getUnassigned = (state: EmailthreadStoreState): Emailthread[] => state.unassigned;
export const getTotalCount = (state: EmailthreadStoreState): number => state.numberOfUnassigned;
export const getAssignedToMe = (state: EmailthreadStoreState): Emailthread[] => state.assignedToMe;
export const getTotalCountOfAssigned = (state: EmailthreadStoreState): number => state.numberOfAssignedToMe;
export const getPatched = (state: EmailthreadStoreState): Emailthread => state.patched;
export const getDetails = (state: EmailthreadStoreState): Emailthread => state.details;

export const selectError: MemoizedSelector<object, any> = createSelector(selectEmailthreadStoreState, getError);
export const selectIsLoading: MemoizedSelector<object, boolean> = createSelector(selectEmailthreadStoreState, isLoading);
export const selectUnassigned: MemoizedSelector<object, Emailthread[]> = createSelector(selectEmailthreadStoreState, getUnassigned);
export const selectUnassignedTotalCount: MemoizedSelector<object, number> = createSelector(selectEmailthreadStoreState, getTotalCount);
export const selectAssigned: MemoizedSelector<object, Emailthread[]> = createSelector(selectEmailthreadStoreState, getAssignedToMe);
export const selectAssignedTotalCount: MemoizedSelector<object, number> = createSelector(selectEmailthreadStoreState, getTotalCountOfAssigned);
export const selectPatched: MemoizedSelector<object, Emailthread> = createSelector(selectEmailthreadStoreState, getPatched);
export const selectDetails: MemoizedSelector<object, Emailthread> = createSelector(selectEmailthreadStoreState, getDetails);
