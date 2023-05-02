import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { PilotAddUpdate, PilotOne } from 'src/app/core/model/pilot.model';
import { User } from 'src/app/core/model/user.model';

import { ToastrService } from 'ngx-toastr';
import { PilotService } from 'src/app/core/service/pilot.service';
import { StorageService } from 'src/app/core/service/storage.service';

@Component({
  selector: 'app-pilot-update',
  templateUrl: './pilot-update.component.html',
  styleUrls: ['./pilot-update.component.css']
})
export class PilotUpdateComponent {

  pilot?: PilotOne
  pilotId?: string
  pilotUpdateDTO?: PilotAddUpdate
  currentUser?: User

  submitted = false;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private pilotService: PilotService,
    private toastrService: ToastrService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    if(! this.storageService.isLoggedIn()) {
      this.toastrService.error("Logging in is required", '', { progressBar: true }); this.onBackToHomePage() 
    }
    this.currentUser = this.storageService.getUser()
    this.activatedRoute.params.subscribe(params => {
      this.pilotId = params['id']
      this.pilotService.getPilot(this.pilotId!).subscribe(
        (pilot: PilotOne) => {
          this.pilot = pilot;
          if (this.pilot.username != this.currentUser?.username && !this.currentUser?.roles.includes("ROLE_ADMIN") && !this.currentUser?.roles.includes("ROLE_MODERATOR")){
            this.toastrService.error("User can not edit this", '', { progressBar: true }); this.onBackToHomePage() 
        }},
        (error) => { this.toastrService.error("Pilot does not exist", '', { progressBar: true }); this.onBackToHomePage()})
    });
  }

  onSubmit() { 
    this.submitted = true; 
    if (this.pilot) {
      this.pilotUpdateDTO = new PilotAddUpdate(this.pilot.firstName, this.pilot.lastName, this.pilot.nationality, this.pilot.date, this.pilot.drivingExperience)
      this.pilotService.updatePilot(this.pilotUpdateDTO, this.pilot.id).subscribe(
        (response) => { this.toastrService.success("Pilot updated successfully", '', { progressBar: true }); this.onBacktoPilotPage() },
        (error) => { this.toastrService.error("Could not update pilot", '', { progressBar: true }); this.onBacktoPilotPage() });
    }
  }

  onBacktoPilotPage() {
    this.router.navigate(['/pilot-component'], { queryParams: { pageNo: 0, pageSize: 25 } })
  }

  onBackToHomePage() {
    this.router.navigate(['/home-page'])
  }

}
