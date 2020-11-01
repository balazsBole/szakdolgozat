import {Component, Input, OnInit} from '@angular/core';
import {Emailthread} from "../../api/models/emailthread";
import {NestedTreeControl} from "@angular/cdk/tree";
import {MatTreeNestedDataSource} from "@angular/material/tree";
import {Email} from "../../api/models/email";
import {ActivatedRoute, ParamMap, Params, Router} from "@angular/router";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'emailthread',
  templateUrl: './emailthread.component.html',
  styleUrls: ['./emailthread.component.css']
})
export class EmailthreadComponent implements OnInit {

  @Input() emailthread: Emailthread;
  @Input('picked') picked: boolean = false;
  @Input() skipLocationChange: boolean = false;
  showMiniatures: boolean;
  @Input() readEmailsWhenClicked: boolean;

  lastMail: Date;
  treeControl = new NestedTreeControl<EmailNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<EmailNode>();
  unread: number;
  allEmail: number;
  hasChild = (_: number, node: EmailNode) => !!node.children && node.children.length > 0;
  private readonly ngUnsubscribe = new Subject();


  constructor(private readonly router: Router, private readonly route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.dataSource.data = orderToEmailNodes(this.emailthread.emails);
    this.unread = this.emailthread.emails.filter(email => !email.read).length;
    this.allEmail = this.emailthread.emails.length;
    this.lastMail = this.emailthread.emails.map(e => new Date(e.processed)).sort()[0];

    this.route.queryParamMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe((paramMap: ParamMap) => {
      this.showMiniatures = this.emailthread.id === paramMap.get('emailThreadId');
    });
  }

  pick() {
    const urlParameters = {
      ...this.route.snapshot.queryParams,
      emailThreadId: this.showMiniatures ? null : this.emailthread.id
    };
    this.updateUrl(urlParameters);
  }

  assign($event: MouseEvent) {
    $event.stopPropagation();
    this.router.navigate(['/assign/' + this.emailthread.id]);
  }

  private updateUrl(urlParameters: Params) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: urlParameters,
      skipLocationChange: this.skipLocationChange
    });
  }
}

interface EmailNode {
  email: Email;
  children?: EmailNode[];
}

function orderToEmailNodes(emails: Array<Email>): EmailNode[] {
  let emailNodes: EmailNode[] = [];
  emails.forEach(e => emailNodes.push({email: e}));

  let rootNodes: EmailNode[] = [];
  emailNodes.forEach(node => {
    if (node.email.parentId === null) {
      rootNodes.push(node);
      return;
    }

    const parent: EmailNode = findParent(emailNodes, node);
    parent.children = [...(parent.children || []), node];
    sortEmailNodesByDate(parent.children);
  });

  sortEmailNodesByDate(rootNodes);
  return rootNodes;

}

function findParent(emailNodes: EmailNode[], node: EmailNode) {
  return emailNodes.find(e => e.email.id === node.email.parentId);
}

function sortEmailNodesByDate(nodes: EmailNode[]) {
  nodes.sort((a: EmailNode, b: EmailNode) => {
    return new Date(a.email.processed).getTime() - new Date(b.email.processed).getTime();
  });
}
