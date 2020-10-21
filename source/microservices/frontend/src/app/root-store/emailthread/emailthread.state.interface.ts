import {Emailthread} from "../../api/models/emailthread";

export const EMAILTHREAD_FEATURE_KEY = 'emailthread';

export interface EmailthreadStoreState {
  emailthreads?: Emailthread[];
  totalCount?: number;
  error?: any;
  loading: boolean;
}

export const initialState: EmailthreadStoreState = {
  loading: false
};
