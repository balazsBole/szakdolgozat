import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailMiniatureComponent} from './email-miniature.component';

describe('EmailMiniatureComponent', () => {
  let component: EmailMiniatureComponent;
  let fixture: ComponentFixture<EmailMiniatureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailMiniatureComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailMiniatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
