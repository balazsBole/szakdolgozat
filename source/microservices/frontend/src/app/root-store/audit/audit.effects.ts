import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';

import {AuditService} from "../../api/services/audit.service";
import {
  getHistoryOfEmailThreadAction,
  getHistoryOfEmailThreadFailAction,
  getHistoryOfEmailThreadSuccessAction,
  getRelatedEmailThreadsAction,
  getRelatedEmailThreadsFailAction,
  getRelatedEmailThreadsSuccessAction
} from "./audit.actions";
import {EmailThread} from "../../api/models/email-thread";
import {EmailThreadAudit} from "../../api/models/email-thread-audit";

@Injectable()
export class AuditEffects {

  getRelatedEmailThreads$ = createEffect(() => this.actions$.pipe(
    ofType(getRelatedEmailThreadsAction),
    mergeMap((action) => this.service.emailThreadsRelatedToUser()
      .pipe(
        map((emailThreadRelated: EmailThread[]) => getRelatedEmailThreadsSuccessAction({emailThreadRelated})),
        catchError((error) => of(getRelatedEmailThreadsFailAction({error})))
      ))
    )
  );

  getHistoryOfaEmailThread$ = createEffect(() => this.actions$.pipe(
    ofType(getHistoryOfEmailThreadAction),
    mergeMap((action) => this.service.emailThread(action.emailThreadId)
      .pipe(
        map((emailThreadHistory: EmailThreadAudit[]) => getHistoryOfEmailThreadSuccessAction({emailThreadHistory})),
        catchError((error) => of(getHistoryOfEmailThreadFailAction({error})))
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: AuditService) {
  }

}
