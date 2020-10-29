import {createFeatureSelector, createSelector, MemoizedSelector} from '@ngrx/store';
import {EMAIL_FEATURE_KEY, EmailStoreState} from './email.state.interface';
import {Email} from "../../api/models";

export const selectEmailStoreState: MemoizedSelector<object, EmailStoreState> = createFeatureSelector<EmailStoreState>(EMAIL_FEATURE_KEY);

export const getError = (state: EmailStoreState): any => state.error;
export const isLoading = (state: EmailStoreState): boolean => state.loading;
export const getEmail = (state: EmailStoreState): Email => state.email;
export const getSentEmail = (state: EmailStoreState): Email => state.sentEmail;

export const selectError: MemoizedSelector<object, any> = createSelector(selectEmailStoreState, getError);
export const selectIsLoading: MemoizedSelector<object, boolean> = createSelector(selectEmailStoreState, isLoading);
export const selectEmail: MemoizedSelector<object, Email> = createSelector(selectEmailStoreState, getEmail);

export const selectSentEmail: MemoizedSelector<object, Email> = createSelector(selectEmailStoreState, getSentEmail);
