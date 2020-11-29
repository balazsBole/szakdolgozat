import {Component, OnDestroy, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";
import {EmailFacade} from "../../root-store/email/email.facade";
import {takeUntil} from "rxjs/operators";
import {Observable, Subject} from "rxjs";
import {Location} from "@angular/common";

@Component({
  selector: 'app-email-reply-view',
  templateUrl: './email-reply-view.component.html',
  styleUrls: ['./email-reply-view.component.css']
})
export class EmailReplyViewComponent implements OnInit, OnDestroy {
  reply: Email;
  loading$: Observable<boolean>;


  parentEmail: Email;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly facade: EmailFacade, private readonly location: Location) {
  }

  ngOnInit(): void {
    this.loading$ = this.facade.loading$;
    this.facade.email$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (email: Email) => {
        this.parentEmail = email
        this.reply = this.facade.createReplyEmail(email);
      });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  exit() {
    this.location.back();
  }

}
