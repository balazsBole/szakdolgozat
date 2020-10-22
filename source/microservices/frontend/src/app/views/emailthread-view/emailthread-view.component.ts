import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";
import {Subject} from "rxjs";
import {Emailthread} from "../../api/models";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-emailthread-view',
  templateUrl: './emailthread-view.component.html',
  styleUrls: ['./emailthread-view.component.css']
})
export class EmailthreadViewComponent implements OnInit, OnDestroy {

  unassignedEmailthreads: Emailthread[];
  numberOfUnassignedEmailthreads: number;
  private readonly ngUnsubscribe = new Subject();

  constructor(private facade: EmailthreadFacade) {
  }

  ngOnInit(): void {
    this.facade.unassigned({});
    this.facade.emailthreads$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: Emailthread[]) => this.unassignedEmailthreads = result);
    this.facade.numberOfElements$
      .pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: number) => this.numberOfUnassignedEmailthreads = result);
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
