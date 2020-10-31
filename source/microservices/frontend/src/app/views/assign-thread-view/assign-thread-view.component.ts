import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {Location} from "@angular/common";
import {takeUntil} from "rxjs/operators";
import {Emailthread} from "../../api/models/emailthread";
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";
import {Email} from "../../api/models/email";
import {ActivatedRoute, ParamMap} from "@angular/router";

@Component({
  selector: 'app-assign-thread-view',
  templateUrl: './assign-thread-view.component.html',
  styleUrls: ['./assign-thread-view.component.css']
})
export class AssignThreadViewComponent implements OnInit {

  email: Email;
  flexContainerHeight: string;
  emailThread: Emailthread;
  userId: string;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly facade: EmailthreadFacade, private readonly location: Location,
              private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.facade.details$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (emailThread: Emailthread) => {
        this.emailThread = emailThread;
      });

    this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      this.email = this.emailThread.emails.find(email => email.id === paramMap.get('emailId'))
    });
    this.calculateFlexContainerHeight()
  }


  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  exit() {
    this.location.back();
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
