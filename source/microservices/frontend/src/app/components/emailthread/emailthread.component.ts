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
  @Input() showMiniatures: boolean = false;

  @Output('emailPicked') pickEmitter = new EventEmitter<Email>();

  unread: number;
  allEmail: number;
  lastMail: Date;
  treeControl = new NestedTreeControl<EmailNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<EmailNode>();
  hasChild = (_: number, node: EmailNode) => !!node.children && node.children.length > 0;
  private pickedEmail: Email;


  constructor() {
  }

  pickEmail(email: Email) {
    if (this.pickedEmail === email) {
      this.pickedEmail = null;
    } else {
      this.pickedEmail = email;
    }
    this.pickEmitter.emit(this.pickedEmail);
  }

  ngOnInit(): void {
    let root = new Array(this.orderToEmailNodes(this.emailthread.emails));
    this.dataSource.data = root;
    this.unread = this.emailthread.emails.filter(email => !email.read).length;
    this.allEmail = this.emailthread.emails.length;
    this.allEmail = this.emailthread.emails.length;
    this.lastMail = this.emailthread.emails.map(e => new Date(e.processed)).sort()[0];
  }

  toggle() {
    this.showMiniatures = !this.showMiniatures;
    if (!this.showMiniatures) {
      this.pickEmail(null);
    }
  }

  private orderToEmailNodes(emails: Array<Email>) {
    let root: EmailNode;

    let emailNodes: EmailNode[] = [];
    emails.forEach(e => emailNodes.push({email: e}));

    emailNodes.forEach(node => {
      if (node.email.header.inReplyTo === null) {
        root = node;
        return;
      }

      const parent: EmailNode = findParent();

      function findParent() {
        return emailNodes.find(e => e.email.header.messageId === node.email.header.inReplyTo);
      }

      parent.children = [...(parent.children || []), node];

      sortByDate();

      function sortByDate() {
        parent.children.sort((a: EmailNode, b: EmailNode) => {
          return new Date(a.email.processed).getTime() - new Date(b.email.processed).getTime();
        });
      }

    });

    return root;
  }
}

interface pelda {
  id: number;
  parentId: number;
  children: pelda[];
}

interface EmailNode {
  email: Email;
  children?: EmailNode[];
}

interface FoodNode {
  name: string;
  children?: FoodNode[];
}

const TREE_DATA: FoodNode[] = [{
  name: 'Vegetables',
  children: [
    {
      name: 'Green',
      children: [
        {name: 'Broccoli'},
        {name: 'Brussels sprouts'},
      ]
    }, {
      name: 'Orange',
      children: [
        {name: 'Pumpkins'},
        {name: 'Carrots'},
      ]
    },
  ]
},
];

