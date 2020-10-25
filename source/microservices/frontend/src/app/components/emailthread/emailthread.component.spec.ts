import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailthreadComponent} from './emailthread.component';

describe('EmailthreadsComponent', () => {
  let component: EmailthreadComponent;
  let fixture: ComponentFixture<EmailthreadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailthreadComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailthreadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
