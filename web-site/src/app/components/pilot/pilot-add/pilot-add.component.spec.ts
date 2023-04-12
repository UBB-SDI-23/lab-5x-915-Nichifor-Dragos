import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotAddComponent } from './pilot-add.component';

describe('PilotAddComponent', () => {
  let component: PilotAddComponent;
  let fixture: ComponentFixture<PilotAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PilotAddComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PilotAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
