import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { RaceAddUpdate, RaceOne } from 'src/app/core/model/race.model';
import { User } from 'src/app/core/model/user.model';

import { ToastrService } from 'ngx-toastr';
import { RaceService } from 'src/app/core/service/race.service';
import { StorageService } from 'src/app/core/service/storage.service';

@Component({
  selector: 'app-race-update',
  templateUrl: './race-update.component.html',
  styleUrls: ['./race-update.component.css']
})
export class RaceUpdateComponent implements OnInit {

  race?: RaceOne
  raceId?: string
  raceUpdateDTO?: RaceAddUpdate
  currentUser?: User

  submitted = false;

  constructor(
    private raceService: RaceService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastrService: ToastrService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    if(! this.storageService.isLoggedIn()) {
      this.toastrService.error("Logging in is required", '', { progressBar: true }); this.onBackToHomePage() 
    }
    this.currentUser = this.storageService.getUser()
    this.activatedRoute.params.subscribe(params => {
      this.raceId = params['id']
      this.raceService.getRace(this.raceId!).subscribe((race: RaceOne) => {
        this.race = race;
        if (this.race.username != this.currentUser?.username && !this.currentUser?.roles.includes("ROLE_ADMIN") && !this.currentUser?.roles.includes("ROLE_MODERATOR")){
          this.toastrService.error("User can not edit this", '', { progressBar: true }); this.onBackToHomePage() 
        }
      },
      (error) => { this.toastrService.error("Pilot does not exist", '', { progressBar: true }); this.onBackToHomePage()})
    });
  }

  onSubmit() { 
    this.submitted = true; 
    if (this.race) {
      this.raceUpdateDTO = new RaceAddUpdate(this.race.name, this.race.country, this.race.numberOfLaps, this.race.lapLength, this.race.date)
      this.raceService.updateRace(this.raceUpdateDTO, this.race.id).subscribe(
        (response) => { this.toastrService.success("Race updated successfully", '', { progressBar: true }); this.onBackToRacePage()},
        (error) => { this.toastrService.error("Could not update race", '', { progressBar: true }); this.onBackToRacePage() });
    }
  }

  onBackToRacePage() {
    this.router.navigate(['/race-component'], { queryParams: { pageNo: 0, pageSize: 25 } })
  }

  onBackToHomePage() {
    this.router.navigate(['/home-page'])
  }

}
