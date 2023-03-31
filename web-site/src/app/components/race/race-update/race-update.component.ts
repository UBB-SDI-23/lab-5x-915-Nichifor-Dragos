import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RaceAddUpdate, RaceOne } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';

@Component({
  selector: 'app-race-update',
  templateUrl: './race-update.component.html',
  styleUrls: ['./race-update.component.css']
})
export class RaceUpdateComponent implements OnInit {

  race?: RaceOne
  raceId?: string
  raceUpdateDTO?: RaceAddUpdate

  submitted = false;

  constructor(
    private raceService: RaceService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.raceId = params['id']
      this.raceService.getRace(this.raceId!).subscribe((race: RaceOne) => {
        this.race = race;
      })
    });
  }

  onSubmit() { 
    this.submitted = true; 
    if (this.race)
    {
      this.raceUpdateDTO = new RaceAddUpdate(this.race.name, this.race.country, this.race.numberOfLaps, this.race.lapLength, this.race.date)
      this.raceService.updateRace(this.raceUpdateDTO, this.race.id)
    }
  }

  onBackToRacePage() {
    this.router.navigateByUrl("race-component")
  }

}
