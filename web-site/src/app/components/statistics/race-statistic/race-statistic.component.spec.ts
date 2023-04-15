import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceStatisticComponent } from './race-statistic.component';

describe('RaceStatisticComponent', () => {
  let component: RaceStatisticComponent;
  let fixture: ComponentFixture<RaceStatisticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceStatisticComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RaceStatisticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
