import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {getDetailsAction, getDetailsFailAction, getDetailsSuccessAction} from './user.actions';
import {UserService} from "../../api/services";
import {User} from "../../api/models/user";
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable()
export class UserEffects {

  getDetails$ = createEffect(() => this.actions$.pipe(
    ofType(getDetailsAction),
    mergeMap((action) => this.service.details()
      .pipe(
        map((user: User) => getDetailsSuccessAction({user})),
        catchError((error) => {
          this._snackBar.open(error.message, "", {duration: 2000});
          return of(getDetailsFailAction({error}))
        })
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: UserService, private readonly _snackBar: MatSnackBar) {
  }

}
