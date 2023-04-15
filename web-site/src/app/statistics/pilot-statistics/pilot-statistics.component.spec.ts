import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotStatisticsComponent } from './pilot-statistics.component';

describe('PilotStatisticsComponent', () => {
  let component: PilotStatisticsComponent;
  let fixture: ComponentFixture<PilotStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PilotStatisticsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PilotStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
