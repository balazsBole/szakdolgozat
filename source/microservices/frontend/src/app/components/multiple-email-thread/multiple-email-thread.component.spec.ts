import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MultipleEmailThreadComponent} from './multiple-email-thread.component';

describe('MultipleEmailThreadComponent', () => {
  let component: MultipleEmailThreadComponent;
  let fixture: ComponentFixture<MultipleEmailThreadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MultipleEmailThreadComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MultipleEmailThreadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
