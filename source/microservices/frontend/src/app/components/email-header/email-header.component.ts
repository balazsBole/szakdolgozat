import {Component, Input, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";
import {EmailFacade} from "../../root-store/email/email.facade";
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";

@Component({
  selector: 'email-header',
  templateUrl: './email-header.component.html',
  styleUrls: ['./email-header.component.css']
})
export class EmailHeaderComponent implements OnInit {

  @Input() email: Email;

  constructor(private readonly facade: EmailFacade, private readonly emailthreadFacade: EmailthreadFacade) {
  }

  ngOnInit(): void {
  }

  markAsUnread() {
    this.facade.markEmailAs(this.email, false);
    this.emailthreadFacade.assignedToMeWith(this.email.emailthread.status);
  }
}
