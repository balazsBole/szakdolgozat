import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";
import {Subject} from "rxjs";
import {Email, Emailthread} from "../../api/models";
import {filter, takeUntil} from "rxjs/operators";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute, ParamMap, Params, Router} from "@angular/router";


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
  emailThreadIdToDisplay: string = "";

  constructor(private readonly facade: EmailthreadFacade, private readonly _snackBar: MatSnackBar,
              private readonly router: Router, private readonly route: ActivatedRoute,) {
  }

  ngOnInit(): void {
    this.updateStatusFromUrl();
    this.assignedToMeWith(this.selectedStatus);
    this.facade.numberOfAssignedThreads$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (number: number) => this.numberOfAssignedThreads = number);
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this._snackBar.open(error.message, "", {duration: 2000})
      });

    this.facade.assignedThreads.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: Emailthread[]) => {
        this.assignedThreads = result
        this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
          if (paramMap.has('emailThreadId')) {
            this.emailThreadIdToDisplay = paramMap.get('emailThreadId');
          }
          if (paramMap.has('emailId') && this.assignedThreads) {
            const emails = this.assignedThreads.map(a => a.emails).reduce((acc, val) => acc.concat(val), []);

            const emailId = paramMap.get('emailId');
            this.emailToDisplay = emails.find(email => email.id === emailId);
          }
        });
      });
  }

  private updateStatusFromUrl() {
    this.route.queryParamMap.pipe(filter((paramMap: ParamMap) => paramMap.has('status')), takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      this.selectedStatus = paramMap.get('status');
    });
  }

  assignedToMeWith(status: string): void {
    const urlParameters : Params = {...this.route.snapshot.queryParams, status: status};
    this.updateUrl(urlParameters);
    this.facade.assignedToMeWith(status)
  }

  private updateUrl(urlParameters: Params) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: urlParameters
    });
  }

  emailThreadPicked(threadId: string) {
    const urlParameters = {...this.route.snapshot.queryParams, emailThreadId: threadId};
    this.updateUrl(urlParameters);

  }

  emailPicked(emailId: string) {
    const urlParameters = {...this.route.snapshot.queryParams, emailId: emailId};
    this.updateUrl(urlParameters);

  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
