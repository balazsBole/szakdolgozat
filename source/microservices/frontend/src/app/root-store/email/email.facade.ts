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
  private sentEmail$: Observable<Email>;

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
}
