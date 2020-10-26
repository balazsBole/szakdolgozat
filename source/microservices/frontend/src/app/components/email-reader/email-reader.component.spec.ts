import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailReaderComponent} from './email-reader.component';

describe('EmailReaderComponent', () => {
  let component: EmailReaderComponent;
  let fixture: ComponentFixture<EmailReaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailReaderComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailReaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
