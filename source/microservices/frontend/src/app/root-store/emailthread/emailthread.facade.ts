import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {patchAction, searchAssignedToMeByStatusAction, searchUnassignedAction} from './emailthread.actions';
import {
  selectAssigned,
  selectAssignedTotalCount,
  selectError,
  selectIsLoading,
  selectPatched,
  selectUnassigned,
  selectUnassignedTotalCount
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
  assignedThreads: Observable<Emailthread[]>;
  numberOfAssignedThreads$: Observable<number>;
  patched$: Observable<Emailthread>;

  constructor(private readonly store: Store<EmailthreadStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.emailthreads$ = this.store.select(selectUnassigned);
    this.numberOfElements$ = this.store.select(selectUnassignedTotalCount);
    this.assignedThreads = this.store.select(selectAssigned);
    this.numberOfAssignedThreads$ = this.store.select(selectAssignedTotalCount);
    this.patched$ = this.store.select(selectPatched);
    this.error$ = this.store.select(selectError);
    this.loading$ = this.store.select(selectIsLoading);
  }

  unassigned(params: EmailthreadService.UnassignedParams) {
    this.store.dispatch(searchUnassignedAction({params}));
  }

  assignedToMeWith(status: string) {
    this.store.dispatch(searchAssignedToMeByStatusAction({status}));
  }

  patch(emalthread: Emailthread) {
    const params: EmailthreadService.PatchParams = {
      body: {"status": emalthread.status, "userId": emalthread.user.id},
      emailThreadId: emalthread.id
    }
    this.store.dispatch(patchAction({params}))
  }

  patchStatus(status: any, id: string) {
    const params: EmailthreadService.PatchParams = {
      body: {"status": status}, emailThreadId: id
    }
    this.store.dispatch(patchAction({params}))
  }


}
