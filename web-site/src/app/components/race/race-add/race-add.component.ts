import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RaceAddUpdate } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-race-add',
  templateUrl: './race-add.component.html',
  styleUrls: ['./race-add.component.css']
})
export class RaceAddComponent implements OnInit{

  submitted = false;
  
  constructor(
    private raceService: RaceService,
    private router: Router,
    private toastrService: ToastrService
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
    if (this.name && this.country && this.lapLength && this.numberOfLaps && this.date) {
      this.model = new RaceAddUpdate(this.name, this. country, this.numberOfLaps, this.lapLength, this.date)
      this.raceService.addRace(this.model).subscribe(
        (response) => { this.toastrService.success("Race added successfully", '', { progressBar: true }); this.onBackToRacePage() },
        error => { this.toastrService.error("Error at adding race", '', { progressBar: true }); this.onBackToRacePage() });;
    } else { this.toastrService.error("Something went wrong", '', { progressBar: true }); this.onBackToRacePage()}
  }

  onBackToRacePage() {
    this.router.navigate(['/race-component'],  { queryParams: { pageNo: 0, pageSize: 25 }} )
  }

}