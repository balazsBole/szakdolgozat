import {Component, Input, OnInit} from '@angular/core';
import {EmailThread} from "../../api/models/email-thread";
import {User} from "../../api/models/user";
import {Subject} from "rxjs";
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {UserFacade} from "../../root-store/user/user.facade";
import {debounceTime, distinctUntilChanged, filter, take, takeUntil} from "rxjs/operators";
import {QueueFacade} from "../../root-store/queue/queue.facade";
import {Queue} from "../../api/models/queue";

@Component({
  selector: 'edit-email-thread',
  templateUrl: './edit-email-thread.component.html',
  styleUrls: ['./edit-email-thread.component.css']
})
export class EditEmailThreadComponent implements OnInit {

  @Input() emailThread: EmailThread;
  @Input() statusEditable: boolean;
  availableUsers: User[];
  assignForm: FormGroup = this.fb.group({
    queueId: ["", Validators.required],
    user: ["", [Validators.required, userValidator()]],
    status: ["", Validators.required]
  })
  private readonly ngUnsubscribe = new Subject();
  private allQueues: Queue[];

  constructor(private readonly facade: EmailThreadFacade, private readonly location: Location,
              private readonly route: ActivatedRoute, private readonly fb: FormBuilder,
              private readonly userFacade: UserFacade, private readonly queueFacade: QueueFacade) {
  }

  getUsername(user: User): string {
    return user?.username || "";
  }

  ngOnInit(): void {
    this.queueFacade.getAll();
    this.queueFacade.queueArray$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (queueArray: Queue[]) => {
        this.allQueues = queueArray;
      });
    this.assignForm.patchValue(
      {
        queueId: this.emailThread.queue.id,
        user: this.emailThread.user,
        status: this.emailThread.status
      });
    this.userFacade.autocomplete$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (userArray: User[]) => {
        this.availableUsers = userArray;
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

  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  exit() {
    this.location.back();
  }

  save() {
    const user = this.assignForm.get('user').value as User;
    const status = this.assignForm.get('status').value as any;
    const queue = this.allQueues.find(q => q.id === this.assignForm.get('queueId').value as string);
    const emailThread = {...this.emailThread, user: user, status: status, queue: queue};
    this.facade.patch(emailThread);
    this.facade.patched$.pipe(
      filter((success: boolean) => success),
      take(1)
    ).subscribe(() => this.exit())
  }
}

export function userValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const valid = control.value?.id || false;
    return valid ? null : {notAUser: {value: control.value}};
  };
}
