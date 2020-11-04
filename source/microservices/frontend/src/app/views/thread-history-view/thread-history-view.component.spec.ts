import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ThreadHistoryViewComponent} from './thread-history-view.component';

describe('ThreadHistoryViewComponent', () => {
  let component: ThreadHistoryViewComponent;
  let fixture: ComponentFixture<ThreadHistoryViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ThreadHistoryViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ThreadHistoryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
