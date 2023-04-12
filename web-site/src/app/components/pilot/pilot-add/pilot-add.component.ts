import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PilotAddUpdate } from 'src/app/core/model/pilot.model';
import { PilotService } from 'src/app/core/service/pilot.service';

@Component({
  selector: 'app-pilot-add',
  templateUrl: './pilot-add.component.html',
  styleUrls: ['./pilot-add.component.css']
})
export class PilotAddComponent {

  submitted = false;
  
  constructor(
    private pilotService: PilotService,
    private router: Router
  ) {}

  firstName ?: string
  lastName ?: string
  nationality ?: string
  date ?: string
  drivingExperience?: number

  model ?: PilotAddUpdate;

  ngOnInit() {
  }

  resetForm() {
    this.firstName = ""
    this.lastName = ""
    this.nationality = ""
    this.date = ""
    this.drivingExperience = undefined
  }

  onSubmit() { 
    this.submitted = true;
    if (this.firstName && this.lastName && this.nationality && this.date && this.drivingExperience)
    {
      this.model = new PilotAddUpdate(this.firstName, this.lastName, this.nationality, this.date, this.drivingExperience)
      this.pilotService.addPilot(this.model).subscribe(
        response => {
          console.log('Race added successfully');
        },
        error => {
          console.error('Error adding race:', error);
        });;
    }
    else
      console.log("Error at creating race")
    this.router.navigateByUrl("pilot-component")
  }

  onBackToRacePage() {
    this.router.navigateByUrl("pilot-component")
  }

}
