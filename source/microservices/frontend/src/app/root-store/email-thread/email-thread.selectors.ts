import {createFeatureSelector, createSelector, MemoizedSelector} from '@ngrx/store';
import {EMAILTHREAD_FEATURE_KEY, EmailThreadStoreState} from './email-thread.state.interface';
import {EmailThread} from "../../api/models/email-thread";
import {EmailThreadVersion} from "../../api/models/email-thread-version";

export const selectEmailThreadStoreState: MemoizedSelector<object, EmailThreadStoreState> = createFeatureSelector<EmailThreadStoreState>(EMAILTHREAD_FEATURE_KEY);

export const getError = (state: EmailThreadStoreState): any => state.error;
export const isLoading = (state: EmailThreadStoreState): boolean => state.loading;
export const getUnassigned = (state: EmailThreadStoreState): EmailThread[] => state.unassigned;
export const getTotalCount = (state: EmailThreadStoreState): number => state.numberOfUnassigned;
export const getAssignedToMe = (state: EmailThreadStoreState): EmailThread[] => state.assignedToMe;
export const getInAssignedQueueWithStatus = (state: EmailThreadStoreState): EmailThread[] => state.inAssignedQueueWithStatus;
export const getTotalCountOfAssigned = (state: EmailThreadStoreState): number => state.numberOfAssignedToMe;
export const getPatched = (state: EmailThreadStoreState): boolean => state.patched;
export const getDetails = (state: EmailThreadStoreState): EmailThreadVersion => state.details;

export const selectError: MemoizedSelector<object, any> = createSelector(selectEmailThreadStoreState, getError);
export const selectIsLoading: MemoizedSelector<object, boolean> = createSelector(selectEmailThreadStoreState, isLoading);
export const selectUnassigned: MemoizedSelector<object, EmailThread[]> = createSelector(selectEmailThreadStoreState, getUnassigned);
export const selectInAssignedQueueWithStatus: MemoizedSelector<object, EmailThread[]> = createSelector(selectEmailThreadStoreState, getInAssignedQueueWithStatus);
export const selectUnassignedTotalCount: MemoizedSelector<object, number> = createSelector(selectEmailThreadStoreState, getTotalCount);
export const selectAssigned: MemoizedSelector<object, EmailThread[]> = createSelector(selectEmailThreadStoreState, getAssignedToMe);
export const selectAssignedTotalCount: MemoizedSelector<object, number> = createSelector(selectEmailThreadStoreState, getTotalCountOfAssigned);
export const selectPatched: MemoizedSelector<object, boolean> = createSelector(selectEmailThreadStoreState, getPatched);
export const selectDetails: MemoizedSelector<object, EmailThreadVersion> = createSelector(selectEmailThreadStoreState, getDetails);
