import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {getDetailsAction} from './user.actions';
import {selectError, selectIsLoading, selectUser} from './user.selectors';
import {UserStoreState} from './user.state.interface';
import {User} from "../../api/models";

@Injectable({providedIn: 'root'})
export class UserFacade {
  user$: Observable<User>;
  error$: Observable<any>;
  loading$: Observable<boolean>;

  constructor(private readonly store: Store<UserStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.user$ = this.store.select(selectUser);
    this.error$ = this.store.select(selectIsLoading);
    this.loading$ = this.store.select(selectError);
  }

  details() {
    this.store.dispatch(getDetailsAction());
  }

}
