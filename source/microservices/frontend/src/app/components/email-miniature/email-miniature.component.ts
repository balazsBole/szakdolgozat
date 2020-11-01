import {Component, Input, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";
import {EmailFacade} from "../../root-store/email/email.facade";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {filter, take, takeUntil} from "rxjs/operators";
import {ActivatedRoute, ParamMap, Params, Router} from "@angular/router";
import {Subject} from "rxjs";

@Component({
  selector: 'email-miniature',
  templateUrl: './email-miniature.component.html',
  styleUrls: ['./email-miniature.component.css']
})
export class EmailMiniatureComponent implements OnInit {

  @Input() email: Email;
  picked: boolean;
  @Input() readEmailsWhenClicked: boolean = true;
  @Input() skipLocationChange: boolean = false;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly facade: EmailFacade, private readonly emailThreadFacade: EmailThreadFacade,
              private readonly router: Router, private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      if (paramMap.has('emailId')) {
        this.picked = this.email.id === paramMap.get('emailId');
      }
    });
  }

  markAsRead() {
    if (this.readEmailsWhenClicked && !this.email.read) {
      this.facade.markEmailAs(this.email, true);
      this.facade.loading$.pipe(
        filter((loading: boolean) => !loading),
        take(1)
      ).subscribe(() => this.emailThreadFacade.assignedToMeWith(this.email.emailThread.status));
    }
  }

  pick() {
    const urlParameters = {...this.route.snapshot.queryParams, emailId: this.email.id};
    this.updateUrl(urlParameters);
  }

  private updateUrl(urlParameters: Params) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: urlParameters,
      skipLocationChange: this.skipLocationChange
    });
  }

}
