import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Email} from "../../api/models/email";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Header} from "../../api/models/header";
import {Content} from "../../api/models/content";
import {Location} from '@angular/common';
import {EmailFacade} from "../../root-store/email/email.facade";
import {EmailThreadFacade} from "../../root-store/email-thread/email-thread.facade";
import {filter, take, takeUntil} from "rxjs/operators";
import {Observable, Subject} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'email-writer',
  templateUrl: './email-writer.component.html',
  styleUrls: ['./email-writer.component.css']
})
export class EmailWriterComponent implements OnInit, OnDestroy {

  @Input("initial") email: Email;
  @Output("emailCreated") sendEmitter = new EventEmitter<Email>();
  private readonly ngUnsubscribe = new Subject();


  emailForm: FormGroup = this.fb.group({
    header: this.fb.group({
      from: ["", [Validators.required, Validators.email]],
      to: ["", [Validators.required, Validators.email]],
      subject: [""]
    }),
    content: this.fb.group({
      body: [""],
      html: [true],
    }),
    status: ["CLARIFICATION", Validators.required]
  })

  quillModules: any = {
    toolbar: {
      container:
        [
          ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
          ['blockquote', 'code-block'],

          [{'header': 1}, {'header': 2}],               // custom button values
          [{'list': 'ordered'}, {'list': 'bullet'}],
          [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
          [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent

          [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
          [{'header': [1, 2, 3, 4, 5, 6, false]}],

          [{'color': []}, {'background': []}],          // dropdown with defaults from theme
          [{'font': []}],
          [{'align': []}],

          ['clean']                                         // remove formatting button
        ]
    }
  };
  loading$: Observable<boolean>;

  constructor(private readonly fb: FormBuilder, private readonly location: Location, private readonly snackBar: MatSnackBar,
              private readonly facade: EmailFacade, private readonly emailThreadFacade: EmailThreadFacade) {
  }

  ngOnInit(): void {
    this.loading$ = this.facade.loading$;
    this.emailThreadFacade.details(this.email.emailThread.id);
    this.facade.error$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (error) => {
        if (error) this.snackBar.open(error.message, "", {duration: 2000})
      });
    if (this.email)
      this.emailForm.patchValue(
        {
          header: {
            from: this.email.header.from,
            to: this.email.header.to,
            subject: this.email.header.subject
          },
          content: {
            body: this.email.content.body
          }
        });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }


  send() {
    const email: Email = this.createEmail();
    this.facade.send(email);

    const status: any = this.emailForm.get('status').value as string;
    this.facade.sentEmail$.pipe(takeUntil(this.ngUnsubscribe)).pipe(filter(value => !!value), take(1)).subscribe((sent: Email) => {
      this.emailThreadFacade.patchStatus(status, sent.emailThread);
      this.sendEmitter.emit(sent);
    });
  }

  exit() {
    this.location.back();
  }

  private createEmail(): Email {
    const header: Header = this.emailForm.get('header').value as Header;

    let email;
    if (this.email) {
      this.email.header.from = header.from;
      this.email.header.to = header.to;
      this.email.header.subject = header.subject;
      email = this.email;
    } else {
      email = {
        content: undefined,
        header: header
      };
    }
    email.content = this.emailForm.get('content').value as Content;

    return email;
  }

}

