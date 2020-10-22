import {Emailthread} from "../../api/models/emailthread";

export const EMAILTHREAD_FEATURE_KEY = 'emailthread';

export interface EmailthreadStoreState {
  unassigned?: Emailthread[];
  numberOfUnassigned?: number;
  assignedToMe?: Emailthread[];
  numberOfAssignedToMe?: number;
  error?: any;
  loading: boolean;
}

export const initialState: EmailthreadStoreState = {
  loading: false
};
