import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {filter, takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {AuditFacade} from "../../root-store/audit/audit.facade";
import {EmailThreadAudit} from "../../api/models/email-thread-audit";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-email-thread-audit',
  templateUrl: './email-thread-audit.component.html',
  styleUrls: ['./email-thread-audit.component.css']
})
export class EmailThreadAuditComponent implements OnInit, OnDestroy {
  show: boolean;
  dataSource: MatTableDataSource<EmailThreadAudit>;
  description?: string;
  displayedColumns: string[] = ['id', 'queue', 'user', 'status', 'type'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  private readonly ngUnsubscribe = new Subject();

  constructor(private readonly route: ActivatedRoute, private readonly auditFacade: AuditFacade) {
  }

  ngOnInit(): void {
    this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      let idFromParam = paramMap.get('emailThreadId');
      this.show = !!idFromParam;
      if (idFromParam)
        this.auditFacade.getHistoryOfThreads(idFromParam);
    });
    this.auditFacade.emailThreadHistory$.pipe(filter(a => !!a), takeUntil(this.ngUnsubscribe)).subscribe(
      (emailThreadHistory: EmailThreadAudit[]) => {
        this.dataSource = new MatTableDataSource(this.initForDisplay(emailThreadHistory));
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    )
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private initForDisplay(emailThreadHistory: EmailThreadAudit[]): EmailThreadAudit[] {
    let clone = [...emailThreadHistory];
    let initialData = [];
    for (let i = 0; i < clone.length; i++) {
      initialData[i] = {...clone[i], id: i + 1};
    }
    return initialData.reverse();
  }
}
