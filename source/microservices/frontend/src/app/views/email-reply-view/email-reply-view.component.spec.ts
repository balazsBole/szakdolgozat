import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailReplyViewComponent} from './email-reply-view.component';

describe('EmailReplyViewComponent', () => {
  let component: EmailReplyViewComponent;
  let fixture: ComponentFixture<EmailReplyViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailReplyViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailReplyViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
