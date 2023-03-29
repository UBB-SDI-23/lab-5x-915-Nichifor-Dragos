import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceAddComponent } from './race-add.component';

describe('RaceAddComponent', () => {
  let component: RaceAddComponent;
  let fixture: ComponentFixture<RaceAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceAddComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RaceAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
