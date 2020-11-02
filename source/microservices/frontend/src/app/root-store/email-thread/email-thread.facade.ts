import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {
  getDetailsAction,
  patchAction,
  searchAssignedToMeByStatusAction,
  searchUnassignedAction
} from './email-thread.actions';
import {
  selectAssigned,
  selectDetails,
  selectError,
  selectIsLoading,
  selectPatched,
  selectUnassigned,
  selectUnassignedTotalCount
} from './email-thread.selectors';
import {EmailThreadStoreState} from './email-thread.state.interface';
import {EmailThreadService} from "../../api/services/email-thread.service";
import {EmailThread} from "../../api/models";

@Injectable({providedIn: 'root'})
export class EmailThreadFacade {
  unassigned$: Observable<EmailThread[]>;
  numberOfUnassigned$: Observable<number>;
  error$: Observable<any>;
  loading$: Observable<boolean>;
  assignedThreads: Observable<EmailThread[]>;
  patched$: Observable<boolean>;
  details$: Observable<EmailThread>;

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
    this.patched$ = this.store.select(selectPatched);
    this.details$ = this.store.select(selectDetails);
    this.error$ = this.store.select(selectError);
    this.loading$ = this.store.select(selectIsLoading);
  }

  unassigned(params: EmailThreadService.UnassignedFromTheQueueParams) {
    this.store.dispatch(searchUnassignedAction({params}));
  }

  assignedToMeWith(status: string) {
    this.store.dispatch(searchAssignedToMeByStatusAction({status}));
  }

  patch(emalthread: EmailThread) {
    const params: EmailThreadService.PatchParams = {
      body: {"status": emalthread.status, "userId": emalthread.user.id},
      emailThreadId: emalthread.id
    }
    this.store.dispatch(patchAction({params}))
  }

  patchStatus(status: any, id: string) {
    const params: EmailThreadService.PatchParams = {
      body: {"status": status}, emailThreadId: id
    }
    this.store.dispatch(patchAction({params}))
  }


}
