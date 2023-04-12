import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PilotService } from 'src/app/core/service/pilot.service';

import { Subscription } from 'rxjs';
import { Pilot } from 'src/app/core/model/pilot.model';

@Component({
  selector: 'app-pilot',
  templateUrl: './pilot.component.html',
  styleUrls: ['./pilot.component.css']
})
export class PilotComponent implements OnInit, OnDestroy{

  subscriptions: Subscription[] = [];
  pilots: Pilot[] = [];

  public constructor (
    private pilotService: PilotService,
    private router: Router
  ) {}

  ngOnInit() {
    this.listPilots();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
}

  listPilots() : void {
    this.subscriptions.push(this.pilotService.listPilots().subscribe(pilots => {
      this.pilots = pilots
    }, error => console.log("Something went wrong " + error)))
  }

  onDeletePilot(id: string) {
    this.pilotService.deletePilot(id).subscribe(
      response => {
        console.log('Race deleted successfully');
        this.listPilots();
      },
      error => {
        console.error('Error deleting race:', error);
      });
  }

}
