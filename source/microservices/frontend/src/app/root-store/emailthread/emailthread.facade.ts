import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {patchAction, searchAssignedToMeByStatusAction, searchUnassignedAction} from './emailthread.actions';
import {
  selectAssigned,
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
  unassigned$: Observable<Emailthread[]>;
  numberOfUnassigned$: Observable<number>;
  error$: Observable<any>;
  loading$: Observable<boolean>;
  assignedThreads: Observable<Emailthread[]>;
  patched$: Observable<Emailthread>;

  constructor(private readonly store: Store<EmailthreadStoreState>) {
    this.initObservables();
  }

  initObservables() {
    this.unassigned$ = this.store.select(selectUnassigned);
    this.numberOfUnassigned$ = this.store.select(selectUnassignedTotalCount);
    this.assignedThreads = this.store.select(selectAssigned);
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
