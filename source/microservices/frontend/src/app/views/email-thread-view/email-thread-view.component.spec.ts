import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailThreadViewComponent} from './email-thread-view.component';

describe('EmailThreadViewComponent', () => {
  let component: EmailThreadViewComponent;
  let fixture: ComponentFixture<EmailThreadViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailThreadViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailThreadViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
