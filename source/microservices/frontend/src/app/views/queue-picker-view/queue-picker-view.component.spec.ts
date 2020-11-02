import {ComponentFixture, TestBed} from '@angular/core/testing';

import {QueuePickerViewComponent} from './queue-picker-view.component';

describe('QueuePickerViewComponent', () => {
  let component: QueuePickerViewComponent;
  let fixture: ComponentFixture<QueuePickerViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [QueuePickerViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QueuePickerViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
