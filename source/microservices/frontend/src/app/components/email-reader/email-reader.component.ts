import {Component, Input, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";

@Component({
  selector: 'email-reader',
  templateUrl: './email-reader.component.html',
  styleUrls: ['./email-reader.component.css']
})
export class EmailReaderComponent implements OnInit {

  @Input() email: Email;
  @Input() title: string;

  constructor() {
  }

  ngOnInit(): void {
  }

}
