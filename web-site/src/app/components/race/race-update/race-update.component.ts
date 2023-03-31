import { Component } from '@angular/core';
import { Race, RaceAdd } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';

@Component({
  selector: 'app-race-update',
  templateUrl: './race-update.component.html',
  styleUrls: ['./race-update.component.css']
})
export class RaceUpdateComponent {

  constructor(
    private raceService: RaceService
  ) {}

  submitted = false;

  model = new RaceAdd('', '', 0, 0 , new Date(2023, 10, 14));

  resetForm() {}

  onSubmit() { 
    this.submitted = true; 
    //this.raceService.updateRace(this.model);
  }

}
