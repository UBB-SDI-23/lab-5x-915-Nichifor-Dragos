import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { RaceOne } from 'src/app/core/model/race.model';

import { ToastrService } from 'ngx-toastr';
import { RaceService } from 'src/app/core/service/race.service';
import { StorageService } from 'src/app/core/service/storage.service';

import { User } from 'src/app/core/model/user.model';

@Component({
  selector: 'app-race-details',
  templateUrl: './race-details.component.html',
  styleUrls: ['./race-details.component.css']
})
export class RaceDetailsComponent implements OnInit{

  isLoggedIn = false;
  currentUser?: User

  race?: RaceOne
  raceId?: string
  nextParticipation: number = 1

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private raceService: RaceService,
    private toastrService: ToastrService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.raceId = params['id']
      this.getRace()
    });
    this.isUserLoggedIn()
  }

  getRace() {
    this.raceService.getRace(this.raceId!).subscribe((race: RaceOne) => {
      this.race = race;
      console.log(race)
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

  isUserLoggedIn() {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = this.storageService.isLoggedIn();
      this.currentUser = this.storageService.getUser();
    }
  }

  isUpdatable(username: string) {
    if (this.currentUser) {
      if (this.currentUser.roles.includes("ROLE_ADMIN") || this.currentUser.roles.includes("ROLE_MODERATOR"))
        return true;
      if (this.isLoggedIn == true && username == this.currentUser.username)
        return true;
    }
    return false;
  }

  isDeletable() {
    if (this.currentUser) {
      if (this.currentUser.roles.includes("ROLE_ADMIN"))
        return true;
    }
    return false;
  }

}
