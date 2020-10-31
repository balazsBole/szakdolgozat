import {Component, OnDestroy, OnInit} from '@angular/core';
import {takeUntil} from "rxjs/operators";
import {Emailthread} from "../../api/models/emailthread";
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Subject} from "rxjs";

@Component({
  selector: 'app-unassigned-view',
  templateUrl: './unassigned-view.component.html',
  styleUrls: ['./unassigned-view.component.css']
})
export class UnassignedViewComponent implements OnInit, OnDestroy {
  threads: Emailthread[];
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly facade: EmailthreadFacade, private readonly _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.facade.unassigned({})
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this._snackBar.open(error.message, "", {duration: 2000})
      });
    this.facade.unassigned$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: Emailthread[]) => {
        this.threads = result
      });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
