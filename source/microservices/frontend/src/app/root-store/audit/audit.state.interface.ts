import {EmailThread} from "../../api/models/email-thread";
import {EmailThreadAudit} from "../../api/models/email-thread-audit";

export const AUDIT_FEATURE_KEY = 'audit';

export interface AuditStoreState {
  emailThreadRelated?: EmailThread[];
  emailThreadHistory?: EmailThreadAudit[];
  error?: any;
  loading: boolean;
}

export const initialState: AuditStoreState = {
  loading: false
};
