import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {Queue} from "../../api/models";
import {getQueuesAction} from "./queue.actions";
import {QueueStoreState} from "./queue.state.interface";
import {selectError, selectIsLoading, selectQueueArray} from "./queue.selectors";

@Injectable({providedIn: 'root'})
export class QueueFacade {
  queueArray$: Observable<Queue[]>;
  error$: Observable<any>;
  loading$: Observable<boolean>;

  constructor(private readonly store: Store<QueueStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.queueArray$ = this.store.select(selectQueueArray);
    this.error$ = this.store.select(selectIsLoading);
    this.loading$ = this.store.select(selectError);
  }

  getAll() {
    this.store.dispatch(getQueuesAction());
  }

}
