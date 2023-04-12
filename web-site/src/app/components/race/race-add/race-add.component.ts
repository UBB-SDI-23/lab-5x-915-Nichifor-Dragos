import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RaceAddUpdate } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';

@Component({
  selector: 'app-race-add',
  templateUrl: './race-add.component.html',
  styleUrls: ['./race-add.component.css']
})
export class RaceAddComponent implements OnInit{

  submitted = false;
  
  constructor(
    private raceService: RaceService,
    private router: Router
  ) {}

  name ?: string
  country ?: string
  date ?: string
  numberOfLaps ?: number
  lapLength?: number

  model ?: RaceAddUpdate;

  ngOnInit() {
  }

  resetForm() {
    this.name = ""
    this.country = ""
    this.date = ""
    this.numberOfLaps = undefined
    this.lapLength = undefined
  }

  onSubmit() { 
    this.submitted = true;
    if (this.name && this.country && this.lapLength && this.numberOfLaps && this.date)
    {
      this.model = new RaceAddUpdate(this.name, this. country, this.numberOfLaps, this.lapLength, this.date)
      this.raceService.addRace(this.model).subscribe(
        response => {
          console.log('Race added successfully');
        },
        error => {
          console.error('Error adding race:', error);
        });;
    }
    else
      console.log("Error at creating race")
    this.router.navigateByUrl("race-component")
  }

  onBackToRacePage() {
    this.router.navigateByUrl("race-component")
  }

}