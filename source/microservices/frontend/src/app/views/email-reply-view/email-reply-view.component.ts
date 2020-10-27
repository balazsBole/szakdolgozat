import {Component, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";
import {EmailFacade} from "../../root-store/email/email.facade";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";

@Component({
  selector: 'app-email-reply-view',
  templateUrl: './email-reply-view.component.html',
  styleUrls: ['./email-reply-view.component.css']
})
export class EmailReplyViewComponent implements OnInit {
  parentEmail: Email;
  email: Email;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly facade: EmailFacade) {
  }

  ngOnInit(): void {

    this.facade.email$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (email: Email) => {
        this.parentEmail = email
        this.email = createReplyEmail(email);
      });

  }


}

function createReplyEmail(email: Email): Email {
  return {
    content: {body: "", html: true},
    direction: "OUT",
    emailthread: email.emailthread,
    header: {
      messageId: "",
      from: email.header.to,
      inReplyTo: email.header.messageId,
      subject: email.header.subject,
      to: email.header.from
    },
    id: "",
    parent: email,
    processed: "",
    read: true
  };
}
