import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmailthreadsComponent} from './emailthreads.component';

describe('EmailthreadsComponent', () => {
  let component: EmailthreadsComponent;
  let fixture: ComponentFixture<EmailthreadsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmailthreadsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailthreadsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
