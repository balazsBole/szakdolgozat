import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailThreadComponent} from './email-thread.component';

describe('EmailThreadsComponent', () => {
  let component: EmailThreadComponent;
  let fixture: ComponentFixture<EmailThreadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailThreadComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailThreadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
