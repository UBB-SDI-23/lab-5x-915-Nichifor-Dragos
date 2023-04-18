import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { RaceAddUpdate, RaceOne } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';
@Component({
  selector: 'app-race-details',
  templateUrl: './race-details.component.html',
  styleUrls: ['./race-details.component.css']
})
export class RaceDetailsComponent implements OnInit{

  race?: RaceOne
  raceId?: string
  nextParticipation: number = 1

  constructor(
    private raceService: RaceService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.raceId = params['id']
      this.getRace()
    });
  }

  getRace() {
    this.raceService.getRace(this.raceId!).subscribe((race: RaceOne) => {
      this.race = race;
      for (let pilot of this.race.pilots)
      {
        if (pilot.startPosition >= this.nextParticipation)
        {
          this.nextParticipation = pilot.startPosition + 1
        }
      }
    })
  }

  onDeleteParticipation(pilotId: string) {
    this.raceService.deleteParticipation(this.raceId!, pilotId).subscribe(
      (response) => { this.toastrService.success("Participation deleted successfully", '', { progressBar: true })
                      this.getRace(); },
      (error) => { this.toastrService.error("Could not delete participation", '', { progressBar: true }) });
  }

  onBackToRacePage() {
    this.router.navigate(['/race-component'],  { queryParams: { pageNo: 0, pageSize: 25 }} )
  }

}
