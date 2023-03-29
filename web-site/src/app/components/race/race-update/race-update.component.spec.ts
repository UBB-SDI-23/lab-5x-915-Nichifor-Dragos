import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceUpdateComponent } from './race-update.component';

describe('RaceUpdateComponent', () => {
  let component: RaceUpdateComponent;
  let fixture: ComponentFixture<RaceUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RaceUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
