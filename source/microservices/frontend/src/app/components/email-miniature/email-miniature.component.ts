import {Component, Input, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";

@Component({
  selector: 'email-miniature',
  templateUrl: './email-miniature.component.html',
  styleUrls: ['./email-miniature.component.css']
})
export class EmailMiniatureComponent implements OnInit {

  @Input() email: Email;

  constructor() {
  }

  ngOnInit(): void {
  }

}
