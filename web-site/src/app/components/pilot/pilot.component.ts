import { Component, OnDestroy, OnInit, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PilotService } from 'src/app/core/service/pilot.service';

import { Subscription } from 'rxjs';
import { Pilot, PilotAll } from 'src/app/core/model/pilot.model';
import { ToastrService } from 'ngx-toastr';
import { PageEvent } from '@angular/material/paginator';

import { User } from 'src/app/core/model/user.model';
import { StorageService } from 'src/app/core/service/storage.service';
import { UserService } from 'src/app/core/service/user.service';

@Component({
  selector: 'app-pilot',
  templateUrl: './pilot.component.html',
  styleUrls: ['./pilot.component.css']
})
export class PilotComponent implements OnInit, OnDestroy {

  isLoggedIn = false;
  currentUser?: User

  pageNumber: number = 0;
  pageSize: number = 25;
  defaultPageSize: number = 20;
  noPages: number = 0;
  goToPageNumber: number = 0;

  subscriptions: Subscription[] = [];
  pilots: PilotAll[] = [];

  public constructor (
    private pilotService: PilotService,
    private router: Router,
    private toastrService: ToastrService,
    private activatedRoute: ActivatedRoute,
    private storageService: StorageService,
    private userService: UserService
  ) {}

  isPc = true;

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.isPc = window.innerWidth > 768;
  }

  ngOnInit() {
    this.onResize();
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
      this.userService.getEntitiesPerPage().subscribe({
        next:(result: number) => {
        this.pageSize = result;
      },
        complete: () => {
          this.pilotService.listPagePilots(this.pageNumber, this.pageSize).subscribe(
            (response) => { this.pilots = response },
            (error) => { this.toastrService.error("Something went wrong", '', { progressBar: true })})
          this.isUserLoggedIn();
          }
      });
    })
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
