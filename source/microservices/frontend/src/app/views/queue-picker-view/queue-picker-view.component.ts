import {Component, OnDestroy, OnInit} from '@angular/core';
import {Queue} from "../../api/models/queue";
import {filter, take, takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {QueueFacade} from "../../root-store/queue/queue.facade";
import {UserFacade} from "../../root-store/user/user.facade";
import {User} from "../../api/models/user";

@Component({
  selector: 'app-queue-picker-view',
  templateUrl: './queue-picker-view.component.html',
  styleUrls: ['./queue-picker-view.component.css']
})
export class QueuePickerViewComponent implements OnInit, OnDestroy {
  allQueues: Queue[];
  selectedId: string;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly queueFacade: QueueFacade, private readonly  userFacade: UserFacade) {
  }

  ngOnInit(): void {
    this.userFacade.details();
    this.queueFacade.getAll();

    this.userFacade.user$.pipe(takeUntil(this.ngUnsubscribe)).pipe(filter(u => !!u)).subscribe(
      (user: User) => {
        this.selectedId = user.queue?.id
      }
    );
    this.queueFacade.queueArray$.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (queueArray: Queue[]) => {
        this.allQueues = queueArray;
      });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  select(id: string) {
    this.userFacade.changeQueue(id);
    this.userFacade.patched$.pipe(
      filter((patched: boolean) => patched),
      take(1)
    ).subscribe(() => this.userFacade.details());
  }
}
