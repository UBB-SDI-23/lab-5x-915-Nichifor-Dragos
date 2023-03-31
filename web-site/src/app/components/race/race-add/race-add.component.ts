import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RaceAddUpdate } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';

@Component({
  selector: 'app-race-add',
  templateUrl: './race-add.component.html',
  styleUrls: ['./race-add.component.css']
})
export class RaceAddComponent {

  submitted = false;

  constructor(
    private raceService: RaceService,
    private router: Router
  ) {}

  model = new RaceAddUpdate('', '', 0, 0, new Date(2023,10,14));

  resetForm() {
    this.model = new RaceAddUpdate('', '' , 0, 0, new Date(2023,10,14))
  }

  onSubmit() { 
    this.submitted = true; 
    this.raceService.addRace(this.model);
  }

  onBackToRacePage() {
    this.router.navigateByUrl("race-component")
  }

}