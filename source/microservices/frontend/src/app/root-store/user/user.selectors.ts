import {createFeatureSelector, createSelector, MemoizedSelector} from '@ngrx/store';
import {USER_FEATURE_KEY, UserStoreState} from './user.state.interface';
import {User} from "../../api/models";

export const selectUserStoreState: MemoizedSelector<object, UserStoreState> = createFeatureSelector<UserStoreState>(USER_FEATURE_KEY);

export const getError = (state: UserStoreState): any => state.error;
export const isLoading = (state: UserStoreState): boolean => state.loading;
export const getUser = (state: UserStoreState): User => state.user;
export const getAutocomplete = (state: UserStoreState): User[] => state.autocomplete;

export const selectError: MemoizedSelector<object, any> = createSelector(selectUserStoreState, getError);
export const selectIsLoading: MemoizedSelector<object, boolean> = createSelector(selectUserStoreState, isLoading);
export const selectUser: MemoizedSelector<object, User> = createSelector(selectUserStoreState, getUser);
export const selectAutocomplete: MemoizedSelector<object, User[]> = createSelector(selectUserStoreState, getAutocomplete);
