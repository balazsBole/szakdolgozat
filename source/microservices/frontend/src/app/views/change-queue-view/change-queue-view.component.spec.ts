import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ChangeQueueViewComponent} from './change-queue-view.component';

describe('ChangeQueueViewComponent', () => {
  let component: ChangeQueueViewComponent;
  let fixture: ComponentFixture<ChangeQueueViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChangeQueueViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeQueueViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
