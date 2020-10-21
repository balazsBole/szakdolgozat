import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {searchUnassignedAction} from './emailthread.actions';
import {
  selectEmailthreadError,
  selectEmailthreadIsLoading,
  selectEmailthreads,
  selectEmailthreadsTotalCount
} from './emailthread.selectors';
import {EmailthreadStoreState} from './emailthread.state.interface';
import {EmailthreadService} from "../../api/services/emailthread.service";
import {Emailthread} from "../../api/models";

@Injectable({providedIn: 'root'})
export class EmailthreadFacade {
  emailthreads$: Observable<Emailthread[]>;
  numberOfElements$: Observable<number>;
  error$: Observable<any>;
  loading$: Observable<boolean>;

  constructor(private readonly store: Store<EmailthreadStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.emailthreads$ = this.store.select(selectEmailthreads);
    this.numberOfElements$ = this.store.select(selectEmailthreadsTotalCount);
    this.error$ = this.store.select(selectEmailthreadError);
    this.loading$ = this.store.select(selectEmailthreadIsLoading);
  }

  unassigned(params: EmailthreadService.UnassignedParams) {
    this.store.dispatch(searchUnassignedAction({params}));
  }


}
