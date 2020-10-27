import {Component, Input, OnInit} from '@angular/core';
import {Email} from "../../api/models/email";

@Component({
  selector: 'email-header',
  templateUrl: './email-header.component.html',
  styleUrls: ['./email-header.component.css']
})
export class EmailHeaderComponent implements OnInit {

  @Input() email: Email;
  @Input() readonly: boolean = true;

  constructor() {
  }

  ngOnInit(): void {
  }

}
