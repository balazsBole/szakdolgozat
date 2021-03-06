import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {of} from 'rxjs';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {
  changeReadAction,
  changeReadFailAction,
  changeReadSuccessAction,
  getDetailsAction,
  getDetailsFailAction,
  getDetailsSuccessAction,
  sendEmailAction,
  sendEmailFailAction,
  sendEmailSuccessAction
} from './email.actions';
import {EmailService} from "../../api/services";
import {Email} from "../../api/models/email";

@Injectable()
export class EmailEffects {

  getDetails$ = createEffect(() => this.actions$.pipe(
    ofType(getDetailsAction),
    mergeMap((action) => this.service.emailDetails(action.id)
      .pipe(
        map((email: Email) => getDetailsSuccessAction({email})),
        catchError((error) => of(getDetailsFailAction({error})))
      ))
    )
  );

  sendEmail$ = createEffect(() => this.actions$.pipe(
    ofType(sendEmailAction),
    mergeMap((action) => this.service.send(action.email)
      .pipe(
        map((email: Email) => sendEmailSuccessAction({email})),
        catchError((error) => of(sendEmailFailAction({error})))
      ))
    )
  );

  changeRead$ = createEffect(() => this.actions$.pipe(
    ofType(changeReadAction),
    mergeMap((action) => this.service.changeRead(action.params)
      .pipe(
        map((email: Email) => changeReadSuccessAction()),
        catchError((error) => of(changeReadFailAction({error})))
      ))
    )
  );

  constructor(private readonly actions$: Actions, private readonly service: EmailService) {
  }

}
