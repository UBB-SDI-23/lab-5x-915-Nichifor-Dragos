import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RaceAddUpdate, RaceOne } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';

@Component({
  selector: 'app-race-update',
  templateUrl: './race-update.component.html',
  styleUrls: ['./race-update.component.css']
})
export class RaceUpdateComponent implements OnInit, OnDestroy {

  race?: RaceOne
  raceId?: string
  raceDTO?: RaceAddUpdate

  constructor(
    private raceService: RaceService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.raceId = params['id']
      this.raceService.getRace(this.raceId!).subscribe((race: RaceOne) => {
        this.race = race;
      })
    });
  }

  ngOnDestroy(): void {
    
  }

  submitted = false;

  model = new RaceAddUpdate('', '', 0, 0 , new Date(2023, 10, 14));

  resetForm() {}

  onSubmit() { 
    this.submitted = true; 
    if (this.race)
    {
      this.raceDTO = new RaceAddUpdate(this.race.name, this.race.country, this.race.numberOfLaps, this.race.lapLength, this.race.date)
      this.raceService.updateRace(this.raceDTO, this.race.id)
    }
  }

}
