import {EmailStoreState} from "./email/email.state.interface";
import {UserStoreState} from "./user/user.state.interface";
import {EmailthreadStoreState} from "./emailthread/emailthread.state.interface";

export interface RootState {
  email: EmailStoreState;
  user: UserStoreState;
  emailthread: EmailthreadStoreState;
}
