import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Email} from "../../api/models/email";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Header} from "../../api/models/header";
import {Content} from "../../api/models/content";
import {Location} from '@angular/common';

export type newEmail = { status: any, email: Email };

@Component({
  selector: 'email-writer',
  templateUrl: './email-writer.component.html',
  styleUrls: ['./email-writer.component.css']
})
export class EmailWriterComponent implements OnInit {

  @Input("initial") email: Email;
  @Output("emailCreated") sendEmitter = new EventEmitter<newEmail>();

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

  constructor(private readonly fb: FormBuilder, private readonly location: Location) {
  }

  ngOnInit(): void {
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

  send() {
    const status: any = this.emailForm.get('status').value as string;
    this.sendEmitter.emit({status: status, email: this.createEmail()});
  }

  discard() {
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

