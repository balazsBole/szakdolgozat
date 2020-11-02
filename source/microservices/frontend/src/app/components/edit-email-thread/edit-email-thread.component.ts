import {Component, Input, OnInit} from '@angular/core';
import {EmailThread} from "../../api/models/email-thread";
import {User} from "../../api/models/user";
import {Subject} from "rxjs";
import {FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {UserFacade} from "../../root-store/user/user.facade";
import {debounceTime, distinctUntilChanged, filter, take, takeUntil} from "rxjs/operators";
import {QueueFacade} from "../../root-store/queue/queue.facade";
import {Queue} from "../../api/models/queue";
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'edit-email-thread',
  templateUrl: './edit-email-thread.component.html',
  styleUrls: ['./edit-email-thread.component.css']
})
export class EditEmailThreadComponent implements OnInit {

  @Input() emailThread: EmailThread;
  availableUsers: User[];

  userAndQueue: FormGroup = this.fb.group({
    queueId: ["", Validators.required],
    user: [""],
  }, {validators: userAndQueueValidator});

  assignForm: FormGroup = this.fb.group({
    userAndQueue: this.userAndQueue,
    status: ["", Validators.required]
  })


  private readonly ngUnsubscribe = new Subject();
  allQueues: Queue[];
  admin: boolean;

  constructor(private readonly facade: EmailThreadFacade, private readonly location: Location,
              private readonly route: ActivatedRoute, private readonly fb: FormBuilder,
              private readonly userFacade: UserFacade, private readonly queueFacade: QueueFacade,
              private readonly keycloakService: KeycloakService) {
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
    this.userFacade.autocomplete$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (userArray: User[]) => {
        this.availableUsers = userArray;
      });
    this.initForms();

  }

  save() {
    const emailThread = this.evaluateForm();
    this.facade.patch(emailThread);
    this.facade.patched$.pipe(
      filter((success: boolean) => success),
      take(1)
    ).subscribe(() => this.exit())
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  exit() {
    this.location.back();
  }

  private initForms() {
    this.admin = this.keycloakService.isUserInRole("admin_user");
    if (!this.admin) this.userAndQueue.controls['user'].setValidators([Validators.required]);
    this.assignForm.patchValue(
      {
        userAndQueue: {
          queueId: this.emailThread.queue.id,
          user: this.emailThread.user
        },
        status: this.emailThread.status
      });
    this.userAndQueue.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((value) => {
        const searchExpression = value.user?.username || value.user || "";
        if (searchExpression.trim().length > 0) {
          this.userFacade.autocomplete(value.queueId, searchExpression.trim());
        } else {
          this.userFacade.reset();
        }
      });
  }

  private evaluateForm() {
    const user = this.userAndQueue.get('user').value as User;
    const status = this.assignForm.get('status').value;
    const queueId = this.userAndQueue.get('queueId').value as string;
    const queue = this.allQueues.find(q => q.id === queueId);
    return {...this.emailThread, user: user, status: status, queue: queue};
  }
}

export const userAndQueueValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  const userEmpty = !control.get('user').value;
  if (userEmpty)
    return null;

  const pickedQueueId = control.get('user').value?.queue?.id || "";
  const queueId = control.get('queueId').value;
  if (pickedQueueId === queueId)
    return null;

  return {userNotFromTheList: true};
};
