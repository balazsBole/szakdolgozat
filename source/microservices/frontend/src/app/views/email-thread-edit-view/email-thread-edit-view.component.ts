import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {Observable, Subject} from "rxjs";
import {Email, EmailThread} from "../../api/models";
import {filter, takeUntil} from "rxjs/operators";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute, ParamMap, Params, Router} from "@angular/router";


@Component({
  selector: 'app-emailThread-view',
  templateUrl: './email-thread-edit-view.component.html',
  styleUrls: ['./email-thread-edit-view.component.css']
})
export class EmailThreadEditViewComponent implements OnInit, OnDestroy {
  loading$: Observable<boolean>;

  private readonly ngUnsubscribe = new Subject();
  assignedThreads: EmailThread[];
  selectedStatus: string = "OPEN";

  emailToDisplay: Email;
  emailThreadIdToDisplay: string = "";

  constructor(private readonly facade: EmailThreadFacade, private readonly snackBar: MatSnackBar,
              private readonly router: Router, private readonly route: ActivatedRoute,) {
  }

  ngOnInit(): void {
    this.loading$ = this.facade.loading$;

    this.updateStatusFromUrl();
    this.assignedToMeWith(this.selectedStatus);
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this.snackBar.open(error.message, "", {duration: 2000})
      });
    this.facade.assignedThreads.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: EmailThread[]) => {
        this.assignedThreads = result
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

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
