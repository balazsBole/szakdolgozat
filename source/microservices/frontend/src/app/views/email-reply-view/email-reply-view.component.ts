import {Component, OnDestroy, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";
import {EmailFacade} from "../../root-store/email/email.facade";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {Location} from "@angular/common";

@Component({
  selector: 'app-email-reply-view',
  templateUrl: './email-reply-view.component.html',
  styleUrls: ['./email-reply-view.component.css']
})
export class EmailReplyViewComponent implements OnInit, OnDestroy {
  reply: Email;

  parentEmail: Email;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly facade: EmailFacade, private readonly location: Location) {
  }

  ngOnInit(): void {
    this.facade.email$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (email: Email) => {
        this.parentEmail = email
        this.reply = createReplyEmail(email);
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

function createReplyEmail(email: Email): Email {
  return {
    content: {body: "", html: true, attachments: []},
    direction: "OUT",
    emailThread: email.emailThread,
    header: {
      from: email.emailThread.queue.email,
      inReplyTo: email.header.messageId,
      references: email.header.references ? email.header.references + " " : "" + email.header.messageId,
      subject: email.header.subject,
      to: email.header.from
    },
    parentId: email.id,
    read: true
  };
}
