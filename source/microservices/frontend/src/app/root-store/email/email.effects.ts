import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {getDetailsAction, getDetailsFailAction, getDetailsSuccessAction} from './email.actions';
import {EmailService} from "../../api/services";
import {Email} from "../../api/models/email";

@Injectable()
export class EmailEffects {

  getDetails$ = createEffect(() => this.actions$.pipe(
    ofType(getDetailsAction),
    mergeMap((action) => this.service.details(action.id)
      .pipe(
        map((email: Email) => getDetailsSuccessAction({email})),
        catchError((error) => of(getDetailsFailAction({error})))
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: EmailService) {
  }

}
