import {EmailStoreState} from "./email/email.state.interface";
import {UserStoreState} from "./user/user.state.interface";
import {EmailThreadStoreState} from "./email-thread/email-thread.state.interface";

export interface RootState {
  email: EmailStoreState;
  user: UserStoreState;
  emailThread: EmailThreadStoreState;
}
