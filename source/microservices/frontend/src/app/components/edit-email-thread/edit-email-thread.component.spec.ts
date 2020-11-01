import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditEmailThreadComponent} from './edit-email-thread.component';

describe('EditEmailThreadComponent', () => {
  let component: EditEmailThreadComponent;
  let fixture: ComponentFixture<EditEmailThreadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditEmailThreadComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditEmailThreadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
