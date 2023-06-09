import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { PilotAddUpdate } from 'src/app/core/model/pilot.model';

import { PilotService } from 'src/app/core/service/pilot.service';
import { ToastrService } from 'ngx-toastr';
import { StorageService } from 'src/app/core/service/storage.service';

@Component({
  selector: 'app-pilot-add',
  templateUrl: './pilot-add.component.html',
  styleUrls: ['./pilot-add.component.css']
})
export class PilotAddComponent {

  submitted = false;
  
  constructor(
    private router: Router,
    private pilotService: PilotService,
    private toastrService: ToastrService,
    private storageService: StorageService
  ) {}

  firstName ?: string
  lastName ?: string
  nationality ?: string
  date ?: string
  drivingExperience?: number

  model ?: PilotAddUpdate;

  ngOnInit() {
    if(! this.storageService.isLoggedIn()) {
      this.toastrService.error("Logging in is required", '', { progressBar: true }); this.onBackToHomePage() 
    }
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
    } else { this.toastrService.error("Something went wrong", '', { progressBar: true }); this.onBacktoPilotPage() }
  }

  onBacktoPilotPage() {
    this.router.navigate(['/pilot-component'], { queryParams: { pageNo: 0, pageSize: 25 }} )
  }

  onBackToHomePage() {
    this.router.navigate(['/home-page'])
  }

}
