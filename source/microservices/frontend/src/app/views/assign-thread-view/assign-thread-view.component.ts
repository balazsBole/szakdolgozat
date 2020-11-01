import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {Location} from "@angular/common";
import {debounceTime, distinctUntilChanged, takeUntil} from "rxjs/operators";
import {Emailthread} from "../../api/models/emailthread";
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";
import {Email} from "../../api/models/email";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {UserFacade} from "../../root-store/user/user.facade";
import {User} from "../../api/models/user";

@Component({
  selector: 'app-assign-thread-view',
  templateUrl: './assign-thread-view.component.html',
  styleUrls: ['./assign-thread-view.component.css']
})
export class AssignThreadViewComponent implements OnInit {

  email: Email;
  flexContainerHeight: string;
  emailThread: Emailthread;
  availableUsers: User[];
  private readonly ngUnsubscribe = new Subject();

  assignForm: FormGroup = this.fb.group({
    user: ["", [Validators.required, userValidator()],]
  })

  constructor(private readonly facade: EmailthreadFacade, private readonly location: Location,
              private readonly route: ActivatedRoute, private readonly fb: FormBuilder,
              private readonly userFacade: UserFacade) {
  }

  getUsername(user: User): string {
    return user?.username || "";
  }

  ngOnInit(): void {
    this.facade.details$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (emailThread: Emailthread) => {
        this.emailThread = emailThread;
      });

    this.userFacade.autocomplete$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (userArray: User[]) => {
        this.availableUsers = userArray;
      });

    this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      this.email = this.emailThread.emails.find(email => email.id === paramMap.get('emailId'))
    });

    this.assignForm.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((value) => {
        const searchExpression = value.user?.username || value.user;
        if (searchExpression.trim().length > 2) {
          this.userFacade.autocomplete(searchExpression.trim());
        } else {
          this.userFacade.reset();
        }
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

  assign() {
    let newVar = this.assignForm.get('user').value as User;
    console.log(newVar.id)
  }
}

export function userValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const valid = control.value?.id || false;
    return valid ? null : {notAUser: {value: control.value}};
  };
}
