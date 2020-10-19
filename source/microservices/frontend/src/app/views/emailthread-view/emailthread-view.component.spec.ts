import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailthreadViewComponent} from './emailthread-view.component';

describe('EmailthreadViewComponent', () => {
  let component: EmailthreadViewComponent;
  let fixture: ComponentFixture<EmailthreadViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailthreadViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailthreadViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
