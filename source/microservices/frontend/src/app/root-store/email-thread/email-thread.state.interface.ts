import {EmailThread} from "../../api/models/email-thread";
import {EmailThreadVersion} from "../../api/models";

export const EMAILTHREAD_FEATURE_KEY = 'emailThread';

export interface EmailThreadStoreState {
  details?: EmailThreadVersion;
  ETag?: string;
  patched?: boolean;
  unassigned?: EmailThread[];
  inAssignedQueueWithStatus?: EmailThread[];
  numberOfUnassigned?: number;
  assignedToMe?: EmailThread[];
  numberOfAssignedToMe?: number;
  error?: any;
  loading: boolean;
}

export const initialState: EmailThreadStoreState = {
  loading: false
};
