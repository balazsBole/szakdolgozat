import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {EmailThread, EmailThreadAudit} from "../../api/models";
import {getHistoryOfEmailThreadAction, getRelatedEmailThreadsAction} from "./audit.actions";
import {AuditStoreState} from "./audit.state.interface";
import {selectEmailThreadHistory, selectError, selectIsLoading, selectRelatedEmailThread} from "./audit.selectors";

@Injectable({providedIn: 'root'})
export class AuditFacade {
  emailThreadRelated$: Observable<EmailThread[]>;
  emailThreadHistory$: Observable<EmailThreadAudit[]>;

  error$: Observable<any>;
  loading$: Observable<boolean>;

  constructor(private readonly store: Store<AuditStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.emailThreadRelated$ = this.store.select(selectRelatedEmailThread)
    this.emailThreadHistory$ = this.store.select(selectEmailThreadHistory)
    this.error$ = this.store.select(selectError);
    this.loading$ = this.store.select(selectIsLoading);
  }

  getRelatedThreads() {
    this.store.dispatch(getRelatedEmailThreadsAction());
  }

  getHistoryOfThreads(emailThreadId: string) {
    this.store.dispatch(getHistoryOfEmailThreadAction({emailThreadId}));
  }

}
