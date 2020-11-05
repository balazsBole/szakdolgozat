import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailThreadAuditComponent} from './email-thread-audit.component';

describe('EmailThreadAuditComponent', () => {
  let component: EmailThreadAuditComponent;
  let fixture: ComponentFixture<EmailThreadAuditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailThreadAuditComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailThreadAuditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
