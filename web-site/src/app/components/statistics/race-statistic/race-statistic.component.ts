import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { RaceStatisticDTO } from 'src/app/core/model/race.model';

import { RaceService } from 'src/app/core/service/race.service';

@Component({
  selector: 'app-race-statistic',
  templateUrl: './race-statistic.component.html',
  styleUrls: ['./race-statistic.component.css']
})
export class RaceStatisticComponent {

  raceStatistics: RaceStatisticDTO[] = []

  constructor (
    private raceService: RaceService,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit() {
    this.listRaces();
  }

  listRaces(): void {
    this.raceService.listRaceStatistic().subscribe(
      (raceStatistics) => { this.raceStatistics = raceStatistics },
      (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
    }

}
