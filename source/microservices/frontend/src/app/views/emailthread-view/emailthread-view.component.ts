import {Component, OnInit} from '@angular/core';
import {EmailthreadFacade} from "../../root-store/emailthread/emailthread.facade";
import {Observable} from "rxjs";
import {Emailthread} from "../../api/models";

@Component({
  selector: 'app-emailthread-view',
  templateUrl: './emailthread-view.component.html',
  styleUrls: ['./emailthread-view.component.css']
})
export class EmailthreadViewComponent implements OnInit {

  emailthreads$: Observable<Emailthread[]>;
  numberOfElements$: Observable<number>;

  constructor(private facade: EmailthreadFacade) {
  }

  ngOnInit(): void {
    this.facade.unassigned({});
    this.emailthreads$ = this.facade.emailthreads$;
    this.numberOfElements$ = this.facade.numberOfElements$;
  }

}
