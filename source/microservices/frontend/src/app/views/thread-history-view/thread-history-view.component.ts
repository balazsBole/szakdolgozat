import {Component, OnDestroy, OnInit} from '@angular/core';
import {EmailThread} from "../../api/models/email-thread";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AuditFacade} from "../../root-store/audit/audit.facade";
import {Observable, Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-thread-history-view',
  templateUrl: './thread-history-view.component.html',
  styleUrls: ['./thread-history-view.component.css']
})
export class ThreadHistoryViewComponent implements OnInit, OnDestroy {
  flexContainerHeight: string;
  threads: EmailThread[];
  private readonly ngUnsubscribe = new Subject();
  loading$: Observable<boolean>;


  constructor(private readonly snackBar: MatSnackBar, private readonly auditFacade: AuditFacade) {
  }

  ngOnInit(): void {
    this.loading$ = this.auditFacade.loading$;
    this.search();
    this.calculateFlexContainerHeight();
    this.auditFacade.emailThreadRelated$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (emailThreadRelated: EmailThread[]) => {
        this.threads = emailThreadRelated;
      });

    this.auditFacade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this.snackBar.open(error.message, "", {duration: 2000})
      });

  }

  search() {
    this.auditFacade.getRelatedThreads();
  }

  private calculateFlexContainerHeight() {
    const height = topOfFlexContainer();
    this.flexContainerHeight = "calc(100vh - " + height + "px)";

    function topOfFlexContainer(): number {
      let top = document.getElementById('flex-container').getBoundingClientRect().top;
      return Math.ceil(top + 3);
    }
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
