import { Component, OnDestroy, OnInit } from '@angular/core';

import { Race } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-race',
  templateUrl: './race.component.html',
  styleUrls: ['./race.component.css']
})
export class RaceComponent implements OnInit, OnDestroy{

  sortedByName = false;

  subscriptions: Subscription[] = [];
  races: Race[] = [];

  public constructor(
    private raceService: RaceService,
    private router: Router,
    private toastrService: ToastrService
    ) {}

  ngOnInit() {
      this.listRaces();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
}

  listRaces(): void {
    this.sortedByName = false;
    this.subscriptions.push(this.raceService.listRaces().subscribe(races => {
      this.races = races
    }, (error) => this.toastrService.error("Something went wrong", '', { progressBar: true })))
  }

  onDeleteRace(id: string) {
    this.raceService.deleteRace(id).subscribe(
      (response) => {
        this.toastrService.success("Race deleted successfully", '', { progressBar: true })
        this.listRaces();
      },
      (error) => {
        this.toastrService.error("Could not delete race", '', { progressBar: true })
      });
  }

  onSortName() {
    this.sortedByName = true;
    this.races.sort((a: Race, b: Race) => {return a.name.localeCompare(b.name)})
  }

}
