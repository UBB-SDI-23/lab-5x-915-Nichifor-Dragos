import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ParticipationAdd } from 'src/app/core/model/race.model';

import { ToastrService } from 'ngx-toastr';
import { PilotService } from 'src/app/core/service/pilot.service';
import { RaceService } from 'src/app/core/service/race.service';

@Component({
  selector: 'app-race-participation-update',
  templateUrl: './race-participation-update.component.html',
  styleUrls: ['./race-participation-update.component.css']
})
export class RaceParticipationUpdateComponent {

  submitted: boolean = false

  raceId?: string
  pilotId?: string
  
  needAccommodation?: boolean

  participation?: ParticipationAdd

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private raceService: RaceService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.raceId = params['idRace']
      this.pilotId = params['idPilot']
      this.raceService.getParticipation(this.pilotId!, this.raceId!).subscribe(
        (participation: ParticipationAdd) => {
        this.participation = participation;
      },
      (error) => { this.toastrService.error("Pilot does not exist", '', { progressBar: true }); this.onBackToRaceDetailsPage()})
    })
  }

  onSubmit() {
    this.submitted = true;
    if (this.needAccommodation && this.participation) {
      const participation: ParticipationAdd = {
        needAccommodation: this.needAccommodation,
        startPosition: this.participation.startPosition
      }

      this.raceService.updateParticipation(participation, this.pilotId!, this.raceId!).subscribe(
        (response) => { this.toastrService.success("Participation updated successfully", '', { progressBar: true }); this.onBackToRaceDetailsPage() },
        (error) => { this.toastrService.error("Could not update participation", '', { progressBar: true }); this.onBackToRaceDetailsPage() });
    }  else { this.toastrService.error("Something went wrong", '', { progressBar: true }); this.onBackToRaceDetailsPage() } 
  }

  onBackToRaceDetailsPage() {
    this.router.navigate(['/race-details-component', this.raceId])
  }

}
