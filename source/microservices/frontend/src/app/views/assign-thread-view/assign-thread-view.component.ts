import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {Location} from "@angular/common";
import {takeUntil} from "rxjs/operators";
import {EmailThread} from "../../api/models/email-thread";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-assign-thread-view',
  templateUrl: './assign-thread-view.component.html',
  styleUrls: ['./assign-thread-view.component.css']
})
export class AssignThreadViewComponent implements OnInit {

  emailThread: EmailThread;
  private readonly ngUnsubscribe = new Subject();


  constructor(private readonly facade: EmailThreadFacade, private readonly location: Location,
              private readonly snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.facade.details$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (emailThread: EmailThread) => {
        this.emailThread = emailThread;
      });
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this.snackBar.open(error.message, "", {duration: 2000})
      });
  }


  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
