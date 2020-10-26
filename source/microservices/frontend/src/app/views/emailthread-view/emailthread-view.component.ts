import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";
import {Subject} from "rxjs";
import {Email, Emailthread} from "../../api/models";
import {takeUntil} from "rxjs/operators";
import {MatSnackBar} from "@angular/material/snack-bar";


@Component({
  selector: 'app-emailthread-view',
  templateUrl: './emailthread-view.component.html',
  styleUrls: ['./emailthread-view.component.css']
})
export class EmailthreadViewComponent implements OnInit, OnDestroy {

  private readonly ngUnsubscribe = new Subject();
  assignedThreads: Emailthread[];
  numberOfAssignedThreads: number;
  selectedStatus: string = "OPEN";
  emailToDisplay: Email;

  constructor(private facade: EmailthreadFacade, private readonly _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.assignedToMeWith(this.selectedStatus);
    this.facade.assignedThreads.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: Emailthread[]) => this.assignedThreads = result);
    this.facade.numberOfAssignedThreads$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (number: number) => this.numberOfAssignedThreads = number);
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this._snackBar.open(error.message, "", {duration: 2000})
      });
  }

  assignedToMeWith(status: string): void {
    this.facade.assignedToMeWith(status)
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
