import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {
  patchAction, patchFailAction, patchSuccessAction,
  searchAssignedToMeByStatusAction,
  searchAssignedToMeFailAction,
  searchAssignedToMeSuccessAction,
  searchUnassignedAction,
  searchUnassignedFailAction,
  searchUnassignedSuccessAction
} from './emailthread.actions';
import {EmailthreadService} from "../../api/services";
import {Emailthread} from "../../api/models/emailthread";

@Injectable()
export class EmailthreadEffects {

  searchUnassigned$ = createEffect(() => this.actions$.pipe(
    ofType(searchUnassignedAction),
    mergeMap((action) => this.service.unassigned(action.params)
      .pipe(
        map((searchResults: Array<Emailthread>) => searchUnassignedSuccessAction({searchResults})),
        catchError((error) => of(searchUnassignedFailAction({error})))
      ))
    )
  );

  searchAssignedToMeByStatus$ = createEffect(() => this.actions$.pipe(
    ofType(searchAssignedToMeByStatusAction),
    mergeMap((action) => this.service.assignedToMeByStatus(action.status)
      .pipe(
        map((searchResults: Array<Emailthread>) => searchAssignedToMeSuccessAction({searchResults})),
        catchError((error) => of(searchAssignedToMeFailAction({error})))
      ))
    )
  );

  patch$ = createEffect(() => this.actions$.pipe(
    ofType(patchAction),
    mergeMap((action) => this.service.patch(action.params)
      .pipe(
        map((emailthread: Emailthread) => patchSuccessAction({emailthread})),
        catchError((error) => of(patchFailAction({error})))
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: EmailthreadService) {
  }

}