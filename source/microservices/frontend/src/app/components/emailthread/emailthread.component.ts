import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Emailthread} from "../../api/models/emailthread";
import {NestedTreeControl} from "@angular/cdk/tree";
import {MatTreeNestedDataSource} from "@angular/material/tree";
import {Email} from "../../api/models/email";

@Component({
  selector: 'emailthread',
  templateUrl: './emailthread.component.html',
  styleUrls: ['./emailthread.component.css']
})
export class EmailthreadComponent implements OnInit {

  @Input() emailthread: Emailthread;
  @Input('picked') showMiniatures: boolean = false;
  @Input('emilToShow') emailId: string;

  @Output('pickedEmailId') pickEmitter = new EventEmitter<string>();

  unread: number;
  allEmail: number;
  lastMail: Date;
  treeControl = new NestedTreeControl<EmailNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<EmailNode>();
  hasChild = (_: number, node: EmailNode) => !!node.children && node.children.length > 0;


  constructor() {
  }

  pickEmail(emailId: string) {
    this.emailId = emailId;
    this.pickEmitter.emit(emailId);
  }

  ngOnInit(): void {
    this.dataSource.data = orderToEmailNodes(this.emailthread.emails);
    this.unread = this.emailthread.emails.filter(email => !email.read).length;
    this.allEmail = this.emailthread.emails.length;
    this.lastMail = this.emailthread.emails.map(e => new Date(e.processed)).sort()[0];
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
