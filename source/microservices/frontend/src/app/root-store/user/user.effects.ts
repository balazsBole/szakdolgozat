import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {getDetailsAction, getDetailsFailAction, getDetailsSuccessAction} from './user.actions';
import {UserService} from "../../api/services";
import {User} from "../../api/models/user";

@Injectable()
export class UserEffects {

  getDetails$ = createEffect(() => this.actions$.pipe(
    ofType(getDetailsAction),
    mergeMap((action) => this.service.details()
      .pipe(
        map((user: User) => getDetailsSuccessAction({user})),
        catchError((error) => of(getDetailsFailAction({error})))
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: UserService) {
  }

}
