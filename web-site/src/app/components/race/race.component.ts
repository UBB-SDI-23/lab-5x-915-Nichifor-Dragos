import { Component, OnDestroy, OnInit, HostListener } from '@angular/core';

import { Race, RaceAll } from 'src/app/core/model/race.model';
import { RaceService } from 'src/app/core/service/race.service';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PageEvent } from '@angular/material/paginator';
import { StorageService } from 'src/app/core/service/storage.service';

import { User } from 'src/app/core/model/user.model';
import { UserService } from 'src/app/core/service/user.service';

@Component({
  selector: 'app-race',
  templateUrl: './race.component.html',
  styleUrls: ['./race.component.css']
})
export class RaceComponent implements OnInit, OnDestroy{

  sortedByName = false;

  subscriptions: Subscription[] = [];
  races: RaceAll[] = [];

  pageNumber: number = 0;
  pageSize: number = 25; 
  noPages: number = 0;
  goToPageNumber: number = 0;

  public constructor(
    private raceService: RaceService,
    private router: Router,
    private toastrService: ToastrService,
    private activatedRoute: ActivatedRoute,
    private storageService: StorageService,
    private userService: UserService
    ) {}

    isLoggedIn = false;
    currentUser?: User

    isPc = true;

    @HostListener('window:resize', ['$event'])
    onResize() {
      this.isPc = window.innerWidth > 768;
    }

  ngOnInit() {
    this.onResize()
    this.userService.getEntitiesPerPage().subscribe((result: number) => {
      this.pageSize = result;
    });
    this.raceService.countRaces().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });
    this.listRaces();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

  listRaces(): void {
    this.sortedByName = false;

    this.activatedRoute.queryParams.subscribe(params => {
      this.pageNumber = Number(params['pageNo']) || 0;
      this.userService.getEntitiesPerPage().subscribe({
        next:(result: number) => {
        this.pageSize = result;
      },
        complete: () => {
          this.raceService.listPageRaces(this.pageNumber, this.pageSize).subscribe(
            (response) => { this.races = response },
            (error) => { this.toastrService.error("Something went wrong", '', { progressBar: true })})
          this.isUserLoggedIn();
          }
      });
    })
  }

  onDeleteRace(id: string) {
    this.raceService.deleteRace(id).subscribe(
      (response) => { this.toastrService.success("Race deleted successfully", '', { progressBar: true })
                      this.listRaces(); },
      (error) => { this.toastrService.error("Could not delete race", '', { progressBar: true }) });
  }

  onSortName() {
    this.sortedByName = true;
    this.races.sort((a: Race, b: Race) => {return a.name.localeCompare(b.name)})
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
    this.raceService.countRaces().subscribe((result: Number) => {
      this.noPages = Math.floor(result.valueOf() / this.pageSize);
      if (result.valueOf() % this.pageSize > 0) {
        this.noPages++;
      }
    });
    this.goToPage()
    this.sortedByName = false;
  }

  goToPage(): void {
    this.pageNumber = Math.min(Math.max(0, this.goToPageNumber), this.noPages);
    const pageIndex = this.pageNumber;
    this.router.navigate(['/race-component'], { queryParams: { pageNo: pageIndex, pageSize: this.pageSize } })
        .then(() => this.listRaces());
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
