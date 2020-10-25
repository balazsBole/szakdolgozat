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

  private readonly ngUnsubscribe = new Subject();
  assignedThreads: Emailthread[];
  selectedStatus: string = "OPEN";

  constructor(private facade: EmailthreadFacade) {
  }

  ngOnInit(): void {
    this.assignedToMeWith(this.selectedStatus);
    this.facade.assignedThreads.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (result: Emailthread[]) => this.assignedThreads = result);
  }

  assignedToMeWith(status: string): void {
    this.facade.assignedToMeWith(status)
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
