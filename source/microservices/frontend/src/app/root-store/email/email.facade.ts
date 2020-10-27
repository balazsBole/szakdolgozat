import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {getDetailsAction} from './email.actions';
import {selectEmail, selectError, selectIsLoading} from './email.selectors';
import {EmailStoreState} from './email.state.interface';
import {Email} from "../../api/models";

@Injectable({providedIn: 'root'})
export class EmailFacade {
  email$: Observable<Email>;
  error$: Observable<any>;
  loading$: Observable<boolean>;

  constructor(private readonly store: Store<EmailStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.email$ = this.store.select(selectEmail);
    this.error$ = this.store.select(selectIsLoading);
    this.loading$ = this.store.select(selectError);
  }

  details(id: string) {
    this.store.dispatch(getDetailsAction({id}));
  }

}
