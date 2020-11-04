import {createFeatureSelector, createSelector, MemoizedSelector} from '@ngrx/store';
import {AUDIT_FEATURE_KEY, AuditStoreState} from './audit.state.interface';
import {EmailThread, EmailThreadAudit} from "../../api/models";

export const selectQueueStoreState: MemoizedSelector<object, AuditStoreState> = createFeatureSelector<AuditStoreState>(AUDIT_FEATURE_KEY);

export const getError = (state: AuditStoreState): any => state.error;
export const isLoading = (state: AuditStoreState): boolean => state.loading;
export const getRelatedEmailThread = (state: AuditStoreState): EmailThread[] => state.emailThreadRelated;
export const getEmailThreadHistory = (state: AuditStoreState): EmailThreadAudit[] => state.emailThreadHistory;

export const selectError: MemoizedSelector<object, any> = createSelector(selectQueueStoreState, getError);
export const selectIsLoading: MemoizedSelector<object, boolean> = createSelector(selectQueueStoreState, isLoading);
export const selectRelatedEmailThread: MemoizedSelector<object, EmailThread[]> = createSelector(selectQueueStoreState, getRelatedEmailThread);
export const selectEmailThreadHistory: MemoizedSelector<object, EmailThreadAudit[]> = createSelector(selectQueueStoreState, getEmailThreadHistory);
