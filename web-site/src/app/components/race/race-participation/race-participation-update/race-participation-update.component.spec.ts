import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceParticipationUpdateComponent } from './race-participation-update.component';

describe('RaceParticipationUpdateComponent', () => {
  let component: RaceParticipationUpdateComponent;
  let fixture: ComponentFixture<RaceParticipationUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceParticipationUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RaceParticipationUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
