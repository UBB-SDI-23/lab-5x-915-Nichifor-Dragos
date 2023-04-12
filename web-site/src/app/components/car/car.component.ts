import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';

import { Car } from 'src/app/core/model/car.model';
import { CarService } from 'src/app/core/service/car.service';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent {

  searchTerm: string = '';

  subscriptions: Subscription[] = [];
  cars: Car[] = []
  carsFiltered: Car[] = []

  constructor(
    private carService: CarService,
    private router: Router,
    private toastrService: ToastrService
    ) {}

    ngOnInit() {
      this.listCars();
    }

    ngOnDestroy(): void {
      this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }

    listCars() {
      this.subscriptions.push(this.carService.listCars().subscribe(cars => {
        this.cars = cars
      }, (error) => this.toastrService.success("Something went wrong", '', { progressBar: true }) ))
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
      this.subscriptions.push(this.carService.listCarsWithCP(this.searchTerm).subscribe(cars => {
        this.carsFiltered = cars
      }, (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }) ))
    }
}
