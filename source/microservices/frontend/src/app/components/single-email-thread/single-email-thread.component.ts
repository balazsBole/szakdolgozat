import {Component, Input, OnInit} from '@angular/core';
import {EmailThread} from "../../api/models/email-thread";
import {Email} from "../../api/models/email";
import {Subject} from "rxjs";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {Location} from "@angular/common";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'single-email-thread',
  templateUrl: './single-email-thread.component.html',
  styleUrls: ['./single-email-thread.component.css']
})
export class SingleEmailThreadComponent implements OnInit {

  @Input() emailThread: EmailThread;
  email: Email;
  flexContainerHeight: string;
  private readonly ngUnsubscribe = new Subject();


  constructor(private readonly facade: EmailThreadFacade, private readonly location: Location,
              private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      this.email = this.emailThread.emails.find(email => email.id === paramMap.get('emailId'))
    });
    this.calculateFlexContainerHeight()
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
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
