import {User} from "../../api/models/user";

export const USER_FEATURE_KEY = 'user';

export interface UserStoreState {
  user?: User;
  autocomplete?: User[];
  error?: any;
  loading: boolean;
}

export const initialState: UserStoreState = {
  loading: false
};
