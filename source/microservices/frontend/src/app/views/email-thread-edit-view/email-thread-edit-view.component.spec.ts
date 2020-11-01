import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailThreadEditViewComponent} from './email-thread-edit-view.component';

describe('EmailThreadViewComponent', () => {
  let component: EmailThreadEditViewComponent;
  let fixture: ComponentFixture<EmailThreadEditViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailThreadEditViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailThreadEditViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
