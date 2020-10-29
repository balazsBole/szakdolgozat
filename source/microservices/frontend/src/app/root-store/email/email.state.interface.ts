import {Email} from "../../api/models/email";

export const EMAIL_FEATURE_KEY = 'email';

export interface EmailStoreState {
  email?: Email;
  sentEmail?: Email;
  error?: any;
  loading: boolean;
}

export const initialState: EmailStoreState = {
  loading: false
};
