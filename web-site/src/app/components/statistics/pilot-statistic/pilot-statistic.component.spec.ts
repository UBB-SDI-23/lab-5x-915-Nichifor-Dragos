import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotStatisticComponent } from './pilot-statistic.component';

describe('PilotStatisticComponent', () => {
  let component: PilotStatisticComponent;
  let fixture: ComponentFixture<PilotStatisticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PilotStatisticComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PilotStatisticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
