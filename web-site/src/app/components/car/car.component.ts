import { Component, HostListener  } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';

import { Car } from 'src/app/core/model/car.model';
import { CarService } from 'src/app/core/service/car.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent {

  pageNumber: number = 0;
  pageSize: number = 25; 
  noPages: number = 0;
  goToPageNumber: number = 0;

  searchTerm: string = '';

  subscriptions: Subscription[] = [];
  cars: Car[] = []

  constructor(
    private carService: CarService,
    private router: Router,
    private toastrService: ToastrService,
    private activatedRoute: ActivatedRoute
    ) {}

    isPc = true;

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.isPc = window.innerWidth > 768;
  }

    ngOnInit() {
      this.onResize();
      this.carService.countCars().subscribe((result: Number) => {
        this.noPages = Math.floor(result.valueOf() / this.pageSize);
        if (result.valueOf() % this.pageSize > 0) {
          this.noPages++;
        }
      });
      this.listCars();
    }

    ngOnDestroy(): void {
      this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

    listCars() {
      this.activatedRoute.queryParams.subscribe(params => {
        this.pageNumber = Number(params['pageNo']) || 0;
        this.pageSize = Number(params['pageSize']) || 50;
      });
      this.carService.listPageCars(this.pageNumber, this.pageSize, this.searchTerm).subscribe(
        (response) => { this.cars = response },
        (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
    }

    onDeleteCar(id: string) {
      this.carService.deleteCar(id).subscribe(
        (response) => {
          this.toastrService.success("Car deleted successfully", '', { progressBar: true })
          this.listCars();
        },
        (error) => {
          this.toastrService.error("Error at deleting car", '', { progressBar: true })
        });
    }

    onSearch() {
      if (this.searchTerm != '' && this.searchTerm != null && this.searchTerm != undefined)
        this.subscriptions.push(this.carService.listPageCars(this.pageNumber, this.pageSize, this.searchTerm).subscribe(cars => {
        this.cars = cars
        this.carService.countCarsCapacity(this.searchTerm).subscribe((result: Number) => {
          this.noPages = Math.floor(result.valueOf() / this.pageSize);
          if (result.valueOf() % this.pageSize > 0) {
            this.noPages++;
          }
        });
      }, (error) => {} ))
      else {
        this.subscriptions.push(this.carService.listPageCars(this.pageNumber, this.pageSize).subscribe(cars => {
          this.cars = cars
          this.carService.countCars().subscribe((result: Number) => {
            this.noPages = Math.floor(result.valueOf() / this.pageSize);
            if (result.valueOf() % this.pageSize > 0) {
              this.noPages++;
            }
          });
        }, (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }) ))
      }
      this.router.navigate(['/car-component'], { queryParams: { pageNo: 0, pageSize: this.pageSize } })
          .then(() => this.listCars());
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
      if (this.searchTerm == '' || this.searchTerm == null || this.searchTerm == undefined) {
        this.carService.countCars().subscribe((result: Number) => {
        this.noPages = Math.floor(result.valueOf() / this.pageSize);
        if (result.valueOf() % this.pageSize > 0) {
          this.noPages++;
        } });
      } else {
        this.carService.countCarsCapacity(this.searchTerm).subscribe((result: Number) => {
          this.noPages = Math.floor(result.valueOf() / this.pageSize);
          if (result.valueOf() % this.pageSize > 0) {
            this.noPages++;
          }
        });
      }
      
      this.goToPage()
    }
  
    goToPage(): void {
      this.pageNumber = Math.min(Math.max(0, this.goToPageNumber), this.noPages);
      const pageIndex = this.pageNumber;
      this.router.navigate(['/car-component'], { queryParams: { pageNo: pageIndex, pageSize: this.pageSize } })
          .then(() => this.listCars());
    }

}
