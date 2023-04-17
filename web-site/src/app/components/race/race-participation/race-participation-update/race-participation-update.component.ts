import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Pilot } from 'src/app/core/model/pilot.model';
import { ParticipationAdd } from 'src/app/core/model/race.model';
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
    private raceService: RaceService,
    private pilotService: PilotService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.raceId = params['idRace']
      this.pilotId = params['idPilot']
      this.raceService.getParticipation(this.pilotId!, this.raceId!).subscribe((participation: ParticipationAdd) => {
        this.participation = participation;
      })
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
    }  else { this.toastrService.error("Could not update participation", '', { progressBar: true }); this.onBackToRaceDetailsPage() } 
  }

  onBackToRaceDetailsPage() {
    this.router.navigate(['/race-details-component', this.raceId])
  }

}
