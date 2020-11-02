import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {
  autocompleteAction,
  autocompleteFailAction,
  autocompleteSuccessAction,
  changeQueueAction,
  changeQueueFailAction,
  changeQueueSuccessAction,
  getDetailsAction,
  getDetailsFailAction,
  getDetailsSuccessAction
} from './user.actions';
import {UserService} from "../../api/services";
import {User} from "../../api/models/user";

@Injectable()
export class UserEffects {

  getDetails$ = createEffect(() => this.actions$.pipe(
    ofType(getDetailsAction),
    mergeMap((action) => this.service.authenticatedUserDetails()
      .pipe(
        map((user: User) => getDetailsSuccessAction({user})),
        catchError((error) => of(getDetailsFailAction({error})))
      ))
    )
  );

  autocomplete$ = createEffect(() => this.actions$.pipe(
    ofType(autocompleteAction),
    mergeMap((action) => this.service.autocomplete(action.params)
      .pipe(
        map((userArray: User[]) => autocompleteSuccessAction({userArray})),
        catchError((error) => of(autocompleteFailAction({error})))
      ))
    )
  );

  changeQueue$ = createEffect(() => this.actions$.pipe(
    ofType(changeQueueAction),
    mergeMap((action) => this.service.changeQueue(action.body)
      .pipe(
        map((userArray: User[]) => changeQueueSuccessAction()),
        catchError((error) => of(changeQueueFailAction({error})))
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: UserService) {
  }

}
