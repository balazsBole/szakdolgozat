import {Component, Input, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";
import {EmailFacade} from "../../root-store/email/email.facade";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {filter, take} from "rxjs/operators";

@Component({
  selector: 'email-header',
  templateUrl: './email-header.component.html',
  styleUrls: ['./email-header.component.css']
})
export class EmailHeaderComponent implements OnInit {

  @Input() email: Email;
  @Input() unread: boolean;
  @Input() reply: boolean;

  constructor(private readonly facade: EmailFacade, private readonly emailThreadFacade: EmailThreadFacade) {
  }

  ngOnInit(): void {
  }

  markAsUnread() {
    this.facade.markEmailAs(this.email, false);
    this.facade.loading$.pipe(
      filter((loading: boolean) => !loading),
      take(1)
    ).subscribe(() => this.emailThreadFacade.assignedToMeWith(this.email.emailThread.status));

  }
}
