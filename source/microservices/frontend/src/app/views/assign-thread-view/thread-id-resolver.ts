import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs';
import {filter, take} from "rxjs/operators";
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";
import {Emailthread} from "../../api/models/emailthread";

@Injectable({providedIn: 'root'})
export class EmailThreadIdResolver implements Resolve<Emailthread> {
  constructor(private readonly facade: EmailthreadFacade) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Emailthread> {
    const uuid: string = route.paramMap.get('uuid');
    this.facade.details(uuid);
    return this.facade.details$.pipe(
      filter((emailThread: Emailthread) => !!emailThread && emailThread.id === uuid),
      take(1)
    );
  }
}
