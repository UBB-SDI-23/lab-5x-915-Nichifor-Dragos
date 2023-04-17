import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PilotAddUpdate } from 'src/app/core/model/pilot.model';
import { PilotService } from 'src/app/core/service/pilot.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-pilot-add',
  templateUrl: './pilot-add.component.html',
  styleUrls: ['./pilot-add.component.css']
})
export class PilotAddComponent {

  submitted = false;
  
  constructor(
    private pilotService: PilotService,
    private router: Router,
    private toastrService: ToastrService
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
    if (this.firstName && this.lastName && this.nationality && this.date && this.drivingExperience) {
      this.model = new PilotAddUpdate(this.firstName, this.lastName, this.nationality, this.date, this.drivingExperience)
      this.pilotService.addPilot(this.model).subscribe(
        (response) => { this.toastrService.success("Pilot added successfully", '', { progressBar: true }); this.onBacktoPilotPage() },
        (error) => { this.toastrService.error("Could not add pilot", '', { progressBar: true }); this.onBacktoPilotPage() });;
    } else { this.toastrService.error("One of the pilot's fields was left empty", '', { progressBar: true }); this.onBacktoPilotPage() }
  }

  onBacktoPilotPage() {
    this.router.navigate(['/pilot-component'])
  }

}
