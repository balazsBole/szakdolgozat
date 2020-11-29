import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {
  getDetailsAction,
  getDetailsFailAction,
  getDetailsSuccessAction,
  patchAction,
  patchFailAction,
  patchSuccessAction,
  searchAssignedToMeByStatusAction,
  searchAssignedToMeFailAction,
  searchAssignedToMeSuccessAction,
  searchByStatusInAssignedQueueAction,
  searchByStatusInAssignedQueueFailAction,
  searchByStatusInAssignedQueueSuccessAction,
  searchUnassignedAction,
  searchUnassignedFailAction,
  searchUnassignedSuccessAction
} from './email-thread.actions';
import {EmailThreadService} from "../../api/services/email-thread.service";
import {EmailThread} from "../../api/models/email-thread";

@Injectable()
export class EmailThreadEffects {


  getDetails$ = createEffect(() => this.actions$.pipe(
    ofType(getDetailsAction),
    mergeMap((action) => this.service.emailThreadDetails(action.id)
      .pipe(
        map((emailThread: EmailThread) => getDetailsSuccessAction({emailThread})),
        catchError((error) => of(getDetailsFailAction({error})))
      ))
    )
  );


  searchUnassigned$ = createEffect(() => this.actions$.pipe(
    ofType(searchUnassignedAction),
    mergeMap((action) => this.service.unassignedFromTheQueue()
      .pipe(
        map((searchResults: Array<EmailThread>) => searchUnassignedSuccessAction({searchResults})),
        catchError((error) => of(searchUnassignedFailAction({error})))
      ))
    )
  );

  searchAssignedToMeByStatus$ = createEffect(() => this.actions$.pipe(
    ofType(searchAssignedToMeByStatusAction),
    mergeMap((action) => this.service.assignedToMeByStatus(action.status)
      .pipe(
        map((searchResults: Array<EmailThread>) => searchAssignedToMeSuccessAction({searchResults})),
        catchError((error) => of(searchAssignedToMeFailAction({error})))
      ))
    )
  );

  searchInAssignedQueueByStatus$ = createEffect(() => this.actions$.pipe(
    ofType(searchByStatusInAssignedQueueAction),
    mergeMap((action) => this.service.searchByStatusInAssignedQueue(action.status)
      .pipe(
        map((searchResults: Array<EmailThread>) => searchByStatusInAssignedQueueSuccessAction({searchResults})),
        catchError((error) => of(searchByStatusInAssignedQueueFailAction({error})))
      ))
    )
  );

  patch$ = createEffect(() => this.actions$.pipe(
    ofType(patchAction),
    mergeMap((action) => this.service.patch(action.params)
      .pipe(
        map(() => patchSuccessAction()),
        catchError((error) => of(patchFailAction({error})))
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: EmailThreadService) {
  }

}
