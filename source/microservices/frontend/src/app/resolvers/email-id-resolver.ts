import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {Email} from "../api/models/email";
import {EmailFacade} from "../root-store/email/email.facade";
import {filter, take} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class EmailIdResolver implements Resolve<Email> {
  constructor(private readonly facade: EmailFacade) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Email> {
    const uuid: string = route.paramMap.get('uuid');
    this.facade.details(uuid);
    return this.facade.email$.pipe(
      filter((email: Email) => !!email && email.id === uuid),
      take(1)
    );
  }
}
