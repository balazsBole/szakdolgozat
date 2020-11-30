import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {
  getDetailsAction,
  patchAction,
  searchAssignedToMeByStatusAction,
  searchByStatusInAssignedQueueAction,
  searchUnassignedAction
} from './email-thread.actions';
import {
  selectAssigned,
  selectDetails,
  selectError,
  selectInAssignedQueueWithStatus,
  selectIsLoading,
  selectPatched,
  selectUnassigned,
  selectUnassignedTotalCount
} from './email-thread.selectors';
import {EmailThreadStoreState} from './email-thread.state.interface';
import {EmailThreadService} from "../../api/services/email-thread.service";
import {EmailThread} from "../../api/models";
import {filter, map} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class EmailThreadFacade {
  unassigned$: Observable<EmailThread[]>;
  numberOfUnassigned$: Observable<number>;
  error$: Observable<any>;
  loading$: Observable<boolean>;
  assignedThreads: Observable<EmailThread[]>;
  inAssignedQueueWithStatus: Observable<EmailThread[]>;
  patched$: Observable<boolean>;
  details$: Observable<EmailThread>;
  private ETag: string;

  constructor(private readonly store: Store<EmailThreadStoreState>) {
    this.initObservables();
  }

  details(id: string) {
    this.store.dispatch(getDetailsAction({id}));
  }

  initObservables() {
    this.unassigned$ = this.store.select(selectUnassigned);
    this.numberOfUnassigned$ = this.store.select(selectUnassignedTotalCount);
    this.assignedThreads = this.store.select(selectAssigned);
    this.inAssignedQueueWithStatus = this.store.select(selectInAssignedQueueWithStatus);
    this.patched$ = this.store.select(selectPatched);
    this.details$ = this.store.select(selectDetails).pipe(filter(emailThreadVersion => !!emailThreadVersion?.emailThread), map(emailThreadVersion => emailThreadVersion.emailThread));
    this.error$ = this.store.select(selectError);
    this.loading$ = this.store.select(selectIsLoading);
    this.store.select(selectDetails).pipe(filter(emailThreadVersion => !!emailThreadVersion)).subscribe(emailThreadVersion => {
      console.log(emailThreadVersion.version)
      this.ETag = "" + emailThreadVersion.version
    });
  }

  unassigned() {
    this.store.dispatch(searchUnassignedAction());
  }

  assignedToMeWith(status: string) {
    this.store.dispatch(searchAssignedToMeByStatusAction({status}));
  }

  waitingForQueueChange() {
    this.store.dispatch(searchByStatusInAssignedQueueAction({status: "CHANGE_QUEUE"}));
  }

  patch(emailThread: EmailThread) {
    const params: EmailThreadService.PatchParams = {
      body: emailThread,
      emailThreadId: emailThread.id,
      ifMatch: this.ETag
    }
    this.store.dispatch(patchAction({params}))
  }

  patchStatus(status: any, original: EmailThread) {
    if (status !== original.status) {
      let newStatus = {...original, status: status};
      this.patch(newStatus);
    }
  }


}
