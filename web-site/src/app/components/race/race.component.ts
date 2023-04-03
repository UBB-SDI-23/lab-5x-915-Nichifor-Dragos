import { Component, OnDestroy, OnInit } from '@angular/core';

import { Race } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

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
    private router: Router) {}

  ngOnInit() {
      this.listRaces();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
}

  listRaces(): void {
    this.sortedByName = false;
    this.subscriptions.push(this.raceService.listRaces().subscribe(races => {
      this.races = races;
    }, error => console.log('Something went wrong ' + error)))
  }

  onUpdateRace(id: string) {
    this.router.navigateByUrl(`race-update-component/${id}`)
  }

  onDeleteRace(id: string) {
    this.raceService.deleteRace(id).subscribe(
      response => {
        console.log('Race deleted successfully');
        this.listRaces();
      },
      error => {
        console.error('Error deleting race:', error);
      });
  }

  onAddRace() {
    this.router.navigateByUrl("race-add-component")
  }

  onSortName() {
    this.sortedByName = true;
    this.races.sort((a: Race, b: Race) => {return a.name.localeCompare(b.name)})
  }

}
