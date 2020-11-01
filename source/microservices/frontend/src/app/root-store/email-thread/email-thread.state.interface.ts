import {EmailThread} from "../../api/models/email-thread";

export const EMAILTHREAD_FEATURE_KEY = 'emailThread';

export interface EmailThreadStoreState {
  details?: EmailThread;
  patched?: boolean;
  unassigned?: EmailThread[];
  numberOfUnassigned?: number;
  assignedToMe?: EmailThread[];
  numberOfAssignedToMe?: number;
  error?: any;
  loading: boolean;
}

export const initialState: EmailThreadStoreState = {
  loading: false
};
