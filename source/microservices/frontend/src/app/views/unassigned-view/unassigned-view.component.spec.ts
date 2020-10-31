import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UnassignedViewComponent} from './unassigned-view.component';

describe('UnassignedViewComponent', () => {
  let component: UnassignedViewComponent;
  let fixture: ComponentFixture<UnassignedViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UnassignedViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UnassignedViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
