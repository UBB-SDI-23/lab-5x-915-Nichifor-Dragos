import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceStatisticsComponent } from './race-statistics.component';

describe('RaceStatisticsComponent', () => {
  let component: RaceStatisticsComponent;
  let fixture: ComponentFixture<RaceStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceStatisticsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RaceStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
