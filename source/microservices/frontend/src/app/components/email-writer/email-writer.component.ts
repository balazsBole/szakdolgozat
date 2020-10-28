import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Email} from "../../api/models/email";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'email-writer',
  templateUrl: './email-writer.component.html',
  styleUrls: ['./email-writer.component.css']
})
export class EmailWriterComponent implements OnInit {

  @Input("initial") email: Email;
  @Output('done') sendEmitter = new EventEmitter<Email>();

  emailForm: FormGroup = this.fb.group({
    header: this.fb.group({
      from: ["", [Validators.required, Validators.email]],
      to: ["", [Validators.required, Validators.email]],
      subject: [""]
    }),
    body: [""],
    html: [true]
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

  constructor(private readonly fb: FormBuilder) {
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
          body: this.email.content.body
        });
  }

  send() {
    if (!this.email) return this.sendEmitter.emit(this.emailForm.value);

    this.email.header.from = this.emailForm.get('header').get('from').value as string;
    this.email.header.to = this.emailForm.get('header').get('to').value as string;
    this.email.header.subject = this.emailForm.get('header').get('subject').value as string;
    this.email.content.body = this.emailForm.get('body').value as string;
    this.email.content.html = true;
    this.sendEmitter.emit(this.email);
  }

}
