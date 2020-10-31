import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AssignThreadViewComponent} from './assign-thread-view.component';

describe('AssignThreadViewComponent', () => {
  let component: AssignThreadViewComponent;
  let fixture: ComponentFixture<AssignThreadViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AssignThreadViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignThreadViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
