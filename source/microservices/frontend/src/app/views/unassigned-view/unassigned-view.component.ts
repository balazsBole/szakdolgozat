import {Component, OnDestroy, OnInit} from '@angular/core';
import {takeUntil} from "rxjs/operators";
import {EmailThread} from "../../api/models/email-thread";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Subject} from "rxjs";

@Component({
  selector: 'app-unassigned-view',
  templateUrl: './unassigned-view.component.html',
  styleUrls: ['./unassigned-view.component.css']
})
export class UnassignedViewComponent implements OnInit, OnDestroy {
  threads: EmailThread[];
  private readonly ngUnsubscribe = new Subject();
  numberOfUnassigned: number;

  constructor(private readonly facade: EmailThreadFacade, private readonly snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.search();
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this.snackBar.open(error.message, "", {duration: 2000})
      });
    this.facade.numberOfUnassigned$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (numberOfUnassigned) => {
        this.numberOfUnassigned = numberOfUnassigned;
      });
    this.facade.unassigned$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: EmailThread[]) => {
        this.threads = result
      });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  search() {
    this.facade.unassigned({});
  }
}
