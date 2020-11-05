import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmailThread} from "../../api/models/email-thread";
import {Observable, Subject} from "rxjs";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {MatSnackBar} from "@angular/material/snack-bar";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-reply-view',
  templateUrl: './reply-view.component.html',
  styleUrls: ['./reply-view.component.css']
})
export class ReplyViewComponent implements OnInit, OnDestroy {
  loading$: Observable<boolean>;
  openThreads: EmailThread[];
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly facade: EmailThreadFacade, private readonly snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.loading$ = this.facade.loading$;
    this.search();
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this.snackBar.open(error.message, "", {duration: 2000})
      });
    this.facade.assignedThreads.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: EmailThread[]) => {
        this.openThreads = result
      });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  search() {
    this.facade.assignedToMeWith("OPEN");
  }

}
