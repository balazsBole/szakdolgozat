import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {autocompleteAction, getDetailsAction, resetAction} from './user.actions';
import {selectAutocomplete, selectError, selectIsLoading, selectUser} from './user.selectors';
import {UserStoreState} from './user.state.interface';
import {User} from "../../api/models";
import {map} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class UserFacade {
  user$: Observable<User>;
  autocomplete$: Observable<User[]>;
  error$: Observable<any>;
  loading$: Observable<boolean>;

  constructor(private readonly store: Store<UserStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.user$ = this.store.select(selectUser);
    this.autocomplete$ = this.store.select(selectAutocomplete);
    this.error$ = this.store.select(selectIsLoading);
    this.loading$ = this.store.select(selectError);
  }

  hasQueue(): Observable<boolean> {
    return this.user$.pipe(map(user => !!user?.queue || false));
  }

  details() {
    this.store.dispatch(getDetailsAction());
  }

  autocomplete(queueId: string, username: string) {
    this.store.dispatch(autocompleteAction({params: {queueId, username}}));
  }

  reset() {
    this.store.dispatch(resetAction());
  }
}
