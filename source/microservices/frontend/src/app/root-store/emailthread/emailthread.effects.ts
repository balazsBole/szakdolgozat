import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import {Action} from '@ngrx/store';
import {Observable, of} from 'rxjs';
import {catchError, map, switchMap} from 'rxjs/operators';
import {searchUnassignedAction, searchUnassignedFailAction, searchUnassignedSuccessAction} from './emailthread.actions';
import {EmailthreadService} from "../../api/services";
import {Emailthread} from "../../api/models/emailthread";

@Injectable()
export class EmailthreadEffects {
  @Effect()
  searchUnassigned$: Observable<Action> = this.actions$.pipe(
    ofType(searchUnassignedAction),
    switchMap((action) => {
      return this.service.unassigned(action.params).pipe(
        map((searchResults: Array<Emailthread>) => searchUnassignedSuccessAction({searchResults}),
          catchError((error) => of(searchUnassignedFailAction({error})))
        )
      );
    })
  );

  constructor(private readonly actions$: Actions, private readonly service: EmailthreadService) {
  }

}
