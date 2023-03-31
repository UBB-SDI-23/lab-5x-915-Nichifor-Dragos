import { Component } from '@angular/core';

import { Race } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-race',
  templateUrl: './race.component.html',
  styleUrls: ['./race.component.css']
})
export class RaceComponent {

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
    this.subscriptions.push(this.raceService.listRaces().subscribe(races => {
      this.races = races;
    }, error => console.log('Something went wrong ' + error)))
  }

  onUpdateRace(id: number) {
    this.router.navigateByUrl(`race-update-component/${id}`)
    console.log("update")
  }

  onDeleteRace(id: number) {
    console.log("delete")
  }

}
