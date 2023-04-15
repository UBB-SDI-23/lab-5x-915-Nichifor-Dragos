import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PilotStatisticDTO } from 'src/app/core/model/pilot.model';
import { PilotService } from 'src/app/core/service/pilot.service';

@Component({
  selector: 'app-pilot-statistic',
  templateUrl: './pilot-statistic.component.html',
  styleUrls: ['./pilot-statistic.component.css']
})
export class PilotStatisticComponent {

  pilotStatistics: PilotStatisticDTO[] = []

  constructor (
    private pilotService: PilotService,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit() {
    this.listPilots();
  }

  listPilots(): void {
    this.pilotService.listPilotStatistic().subscribe(
      (pilotStatistics) => { this.pilotStatistics = pilotStatistics },
      (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
    }

}
