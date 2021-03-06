import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {changeReadAction, getDetailsAction, sendEmailAction} from './email.actions';
import {selectEmail, selectError, selectIsLoading, selectSentEmail} from './email.selectors';
import {EmailStoreState} from './email.state.interface';
import {Email} from "../../api/models";
import {EmailService} from "../../api/services/email.service";

@Injectable({providedIn: 'root'})
export class EmailFacade {
  email$: Observable<Email>;
  error$: Observable<any>;
  loading$: Observable<boolean>;
  sentEmail$: Observable<Email>;

  constructor(private readonly store: Store<EmailStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.email$ = this.store.select(selectEmail);
    this.error$ = this.store.select(selectError);
    this.loading$ = this.store.select(selectIsLoading);
    this.sentEmail$ = this.store.select(selectSentEmail)
  }

  details(id: string) {
    this.store.dispatch(getDetailsAction({id}));
  }

  send(email: Email) {
    this.store.dispatch(sendEmailAction({email}));
  }

  markEmailAs(email: Email, read: boolean) {
    const params: EmailService.ChangeReadParams = {body: {"read": read}, emailId: email.id}
    this.store.dispatch(changeReadAction({params}));
  }

  createReplyEmail(email: Email): Email {
    return {
      content: {body: "", html: true, attachments: []},
      direction: "OUT",
      emailThread: email.emailThread,
      header: {
        from: email.emailThread.queue.email,
        inReplyTo: email.header.messageId,
        references: email.header.references ? email.header.references + " " : "" + email.header.messageId,
        subject: email.header.subject,
        to: email.header.from
      },
      parentId: email.id,
      read: true
    };
  }

}
