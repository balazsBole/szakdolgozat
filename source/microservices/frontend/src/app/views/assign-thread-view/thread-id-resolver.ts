import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {filter, take} from "rxjs/operators";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {EmailThread} from "../../api/models/email-thread";

@Injectable({providedIn: 'root'})
export class EmailThreadIdResolver implements Resolve<EmailThread> {
  constructor(private readonly facade: EmailThreadFacade) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<EmailThread> {
    const uuid: string = route.paramMap.get('uuid');
    this.facade.details(uuid);
    return this.facade.details$.pipe(
      filter((emailThread: EmailThread) => !!emailThread && emailThread.id === uuid),
      take(1)
    );
  }
}
