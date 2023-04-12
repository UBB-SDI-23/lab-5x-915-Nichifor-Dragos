import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PilotService } from 'src/app/core/service/pilot.service';

import { Subscription } from 'rxjs';
import { Pilot } from 'src/app/core/model/pilot.model';
import { ToastrService } from 'ngx-toastr';

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
    private router: Router,
    private toastrService: ToastrService
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
    }, (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }) ))
  }

  onDeletePilot(id: string) {
    this.pilotService.deletePilot(id).subscribe(
      (response) => {
        this.toastrService.success("Pilot deleted successfully", '', { progressBar: true })
        this.listPilots();
      }, (error) => {
        this.toastrService.error("Could not delete pilot", '', { progressBar: true })
      });
  }

}
