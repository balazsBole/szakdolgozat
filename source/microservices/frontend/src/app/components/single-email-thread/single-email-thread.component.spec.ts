import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SingleEmailThreadComponent} from './single-email-thread.component';

describe('SingleEmailThreadComponent', () => {
  let component: SingleEmailThreadComponent;
  let fixture: ComponentFixture<SingleEmailThreadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SingleEmailThreadComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleEmailThreadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
