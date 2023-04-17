import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceParticipationAddComponent } from './race-participation-add.component';

describe('RaceParticipationAddComponent', () => {
  let component: RaceParticipationAddComponent;
  let fixture: ComponentFixture<RaceParticipationAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceParticipationAddComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RaceParticipationAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
