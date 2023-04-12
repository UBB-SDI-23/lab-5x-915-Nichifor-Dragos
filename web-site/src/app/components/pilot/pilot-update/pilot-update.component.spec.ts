import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotUpdateComponent } from './pilot-update.component';

describe('PilotUpdateComponent', () => {
  let component: PilotUpdateComponent;
  let fixture: ComponentFixture<PilotUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PilotUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PilotUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
