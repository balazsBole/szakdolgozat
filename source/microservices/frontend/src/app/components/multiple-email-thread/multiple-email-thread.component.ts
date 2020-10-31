import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {Subject} from "rxjs";
import {Emailthread} from "../../api/models/emailthread";
import {ActivatedRoute, ParamMap, Params, Router} from "@angular/router";
import {takeUntil} from "rxjs/operators";
import {Email} from "../../api/models/email";

@Component({
  selector: 'multiple-email-thread',
  templateUrl: './multiple-email-thread.component.html',
  styleUrls: ['./multiple-email-thread.component.css']
})
export class MultipleEmailThreadComponent implements OnInit, OnDestroy, OnChanges {
  @Input() threads: Emailthread[];
  @Input() unread: boolean;
  @Input() reply: boolean;
  email: Email;
  emailThreadId: string;
  flexContainerHeight: string;
  private emailId: string;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly router: Router, private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      if (paramMap.has('emailThreadId')) {
        this.emailThreadId = paramMap.get('emailThreadId');
      }
      if (paramMap.has('emailId')) {
        this.emailId = paramMap.get('emailId');
      }
      if (this.threads)
        this.email = this.getEmailFrom(this.threads);
    });
    this.calculateFlexContainerHeight()

  }

  ngOnChanges(changes: SimpleChanges) {
    this.calculateFlexContainerHeight()
    let availableThreads = changes.threads.currentValue;
    if (availableThreads)
      this.email = this.getEmailFrom(availableThreads);
  }

  emailThreadPicked(threadId: string) {
    const urlParameters = {...this.route.snapshot.queryParams, emailThreadId: threadId};
    this.updateUrl(urlParameters);
  }

  emailPicked(emailId: string) {
    const urlParameters = {...this.route.snapshot.queryParams, emailId: emailId};
    this.updateUrl(urlParameters);
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private getEmailFrom(availableThreads) {
    const emails = availableThreads.find(thread => thread.id === this.emailThreadId)?.emails;
    return emails?.find(email => email.id === this.emailId);
  }

  private updateUrl(urlParameters: Params) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: urlParameters
    });
  }

  private calculateFlexContainerHeight() {
    const height = topOfFlexContainer();
    this.flexContainerHeight = "calc(100vh - " + height + "px)";

    function topOfFlexContainer(): number {
      let top = document.getElementById('flex-container').getBoundingClientRect().top;
      return Math.ceil(top + 2);
    }
  }

}
