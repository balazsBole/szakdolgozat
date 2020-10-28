import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailWriterComponent} from './email-writer.component';

describe('EmailWriterComponent', () => {
  let component: EmailWriterComponent;
  let fixture: ComponentFixture<EmailWriterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailWriterComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailWriterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
