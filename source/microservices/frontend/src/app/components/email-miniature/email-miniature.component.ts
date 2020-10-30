import {Component, Input, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";
import {EmailFacade} from "../../root-store/email/email.facade";
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";

@Component({
  selector: 'email-miniature',
  templateUrl: './email-miniature.component.html',
  styleUrls: ['./email-miniature.component.css']
})
export class EmailMiniatureComponent implements OnInit {

  @Input() email: Email;
  @Input() picked: boolean;

  constructor(private readonly facade: EmailFacade, private readonly emailthreadFacade: EmailthreadFacade) {
  }

  ngOnInit(): void {
  }

  markAsRead() {
    if (!this.email.read) {
      this.facade.markEmailAs(this.email, true);
      this.emailthreadFacade.assignedToMeWith(this.email.emailthread.status);
    }
  }
}
