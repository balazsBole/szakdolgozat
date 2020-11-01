import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditThreadViewComponent} from './edit-thread-view.component';

describe('EditThreadViewComponent', () => {
  let component: EditThreadViewComponent;
  let fixture: ComponentFixture<EditThreadViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditThreadViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditThreadViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
