import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {getDetailsAction, sendEmailAction} from './email.actions';
import {selectEmail, selectError, selectIsLoading, selectSentEmail} from './email.selectors';
import {EmailStoreState} from './email.state.interface';
import {Email} from "../../api/models";

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
    this.error$ = this.store.select(selectIsLoading);
    this.loading$ = this.store.select(selectError);
    this.sentEmail$ = this.store.select(selectSentEmail)
  }

  details(id: string) {
    this.store.dispatch(getDetailsAction({id}));
  }

  send(email: Email) {
    this.store.dispatch(sendEmailAction({email}));
  }

}
