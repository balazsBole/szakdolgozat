import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';

import {Queue} from "../../api/models/queue";
import {QueueService} from "../../api/services/queue.service";
import {getQueuesAction, getQueuesFailAction, getQueuesSuccessAction} from "./queue.actions";

@Injectable()
export class QueueEffects {

  getAll$ = createEffect(() => this.actions$.pipe(
    ofType(getQueuesAction),
    mergeMap((action) => this.service.getAll()
      .pipe(
        map((queueArray: Queue[]) => getQueuesSuccessAction({queueArray})),
        catchError((error) => of(getQueuesFailAction({error})))
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: QueueService) {
  }

}
