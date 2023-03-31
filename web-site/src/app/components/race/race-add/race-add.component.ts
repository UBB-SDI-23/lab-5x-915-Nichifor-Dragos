import { Component } from '@angular/core';
import { RaceAddUpdate } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';

@Component({
  selector: 'app-race-add',
  templateUrl: './race-add.component.html',
  styleUrls: ['./race-add.component.css']
})
export class RaceAddComponent {

  constructor(
    private raceService: RaceService
  ) {}

  submitted = false;

  model = new RaceAddUpdate('', '', 0, 0, new Date(2023,10,14));

  resetForm() {this.model = new RaceAddUpdate('', '' , 0, 0, new Date(2023,10,14))}

  onSubmit() { 
    this.submitted = true; 
    this.raceService.addRace(this.model);
  }
}