import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmailThread} from "../../api/models/email-thread";
import {Observable, Subject} from "rxjs";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {MatSnackBar} from "@angular/material/snack-bar";
import {filter, takeUntil} from "rxjs/operators";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-edit-thread-view',
  templateUrl: './edit-thread-view.component.html',
  styleUrls: ['./edit-thread-view.component.css']
})
export class EditThreadViewComponent implements OnInit, OnDestroy {
  loading$: Observable<boolean>;

  emailThread: EmailThread;
  private readonly ngUnsubscribe = new Subject();


  constructor(private readonly facade: EmailThreadFacade,
              private readonly snackBar: MatSnackBar, private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.emailThread = this.route.snapshot.data['emailThread'];
    this.loading$ = this.facade.loading$;
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe), filter(error => !!error)).subscribe(
      (error) => {
        if (error.status == 409) {
          this.snackBar.open("Please reload the page, email thread concurrently changed", "", {duration: 2000})
        } else
          this.snackBar.open(error.message, "", {duration: 2000})
      });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
