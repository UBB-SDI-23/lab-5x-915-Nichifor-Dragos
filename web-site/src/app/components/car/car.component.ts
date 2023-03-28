import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { Car } from 'src/app/core/model/car.model';
import { CarService } from 'src/app/core/service/car.service';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent {

  subscriptions: Subscription[] = [];
  cars: Car[] = []

  constructor(
    private carService: CarService,
    private router: Router
    ) {}

    ngOnInit() {
      this.listCars();
    }

    listCars() {
      this.subscriptions.push(this.carService.listRaces().subscribe(cars => {
        this.cars = cars
      }, error => console.log("Something went wrong " + error)))
    }
}
