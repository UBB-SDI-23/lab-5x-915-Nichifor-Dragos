import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PilotAddUpdate, PilotOne } from 'src/app/core/model/pilot.model';
import { PilotService } from 'src/app/core/service/pilot.service';

@Component({
  selector: 'app-pilot-update',
  templateUrl: './pilot-update.component.html',
  styleUrls: ['./pilot-update.component.css']
})
export class PilotUpdateComponent {

  pilot?: PilotOne
  pilotId?: string
  pilotUpdateDTO?: PilotAddUpdate

  submitted = false;

  constructor(
    private pilotService: PilotService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.pilotId = params['id']
      this.pilotService.getPilot(this.pilotId!).subscribe((pilot: PilotOne) => {
        this.pilot = pilot;
      })
    });
  }

  onSubmit() { 
    this.submitted = true; 
    if (this.pilot) {
      this.pilotUpdateDTO = new PilotAddUpdate(this.pilot.firstName, this.pilot.lastName, this.pilot.nationality, this.pilot.date, this.pilot.drivingExperience)
      this.pilotService.updatePilot(this.pilotUpdateDTO, this.pilot.id).subscribe(
        (response) => { this.toastrService.success("Pilot updated successfully", '', { progressBar: true }) },
        (error) => { this.toastrService.error("Could not update pilot", '', { progressBar: true }) });
    }
    this.onBackToRacePage()
  }

  onBackToRacePage() {
    this.router.navigateByUrl("race-component")
  }

}
