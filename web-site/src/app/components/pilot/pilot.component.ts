import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PilotService } from 'src/app/core/service/pilot.service';

import { Subscription } from 'rxjs';
import { Pilot } from 'src/app/core/model/pilot.model';
import { ToastrService } from 'ngx-toastr';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-pilot',
  templateUrl: './pilot.component.html',
  styleUrls: ['./pilot.component.css']
})
export class PilotComponent implements OnInit, OnDestroy{

  pageNumber: number = 0;
  pageSize: number = 25; 
  noPages: number = 0;
  goToPageNumber: number = 0;

  subscriptions: Subscription[] = [];
  pilots: Pilot[] = [];

  public constructor (
    private pilotService: PilotService,
    private router: Router,
    private toastrService: ToastrService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.pilotService.countPilots().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });

    this.listPilots();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
}

  listPilots() : void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      this.pageSize = Number(params['pageSize']) || 50;
    });
    this.pilotService.listPagePilots(this.pageNumber, this.pageSize).subscribe(
      (response) => { this.pilots = response },
      (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
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

  checkPageNumber(): void {
    if (this.goToPageNumber > this.noPages) {
      this.goToPageNumber = this.noPages;
    }
  }

  onPageChanged(event: PageEvent) {
    this.pageNumber = event.pageIndex;
    this.goToPageNumber = this.pageNumber;
    this.pageSize = event.pageSize;   
    this.pilotService.countPilots().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });
    this.goToPage()
  }

  goToPage(): void {
    this.pageNumber = Math.min(Math.max(0, this.goToPageNumber), this.noPages);
    const pageIndex = this.pageNumber;
    this.router.navigate(['/pilot-component'], { queryParams: { pageNo: pageIndex, pageSize: this.pageSize } })
        .then(() => this.listPilots());
  }

}
