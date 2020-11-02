import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {Subject} from "rxjs";
import {EmailThread} from "../../api/models/email-thread";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {takeUntil} from "rxjs/operators";
import {Email} from "../../api/models/email";

@Component({
  selector: 'multiple-email-thread',
  templateUrl: './multiple-email-thread.component.html',
  styleUrls: ['./multiple-email-thread.component.css']
})
export class MultipleEmailThreadComponent implements OnInit, OnDestroy, OnChanges {
  @Input() threads: EmailThread[];
  @Input() markAsRead: boolean;
  @Input() reply: boolean;
  @Input() editable: boolean;
  email: Email;
  emailThreadId: string;
  flexContainerHeight: string;
  private emailId: string;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly router: Router, private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      if (paramMap.has('emailThreadId') && paramMap.has('emailId')) {
        this.emailThreadId = paramMap.get('emailThreadId');
        this.emailId = paramMap.get('emailId');
        if (this.threads) this.email = this.getEmailFrom(this.threads);
      } else {
        this.email = null
      }
    });
    this.calculateFlexContainerHeight()

  }

  ngOnChanges(changes: SimpleChanges) {
    this.calculateFlexContainerHeight()
    let availableThreads = changes.threads.currentValue;
    if (availableThreads)
      this.email = this.getEmailFrom(availableThreads);
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private getEmailFrom(availableThreads) {
    const emails = availableThreads.find(thread => thread.id === this.emailThreadId)?.emails;
    return emails?.find(email => email.id === this.emailId);
  }


  private calculateFlexContainerHeight() {
    const height = topOfFlexContainer();
    this.flexContainerHeight = "calc(100vh - " + height + "px)";

    function topOfFlexContainer(): number {
      let top = document.getElementById('flex-container').getBoundingClientRect().top;
      return Math.ceil(top + 3);
    }
  }

}
