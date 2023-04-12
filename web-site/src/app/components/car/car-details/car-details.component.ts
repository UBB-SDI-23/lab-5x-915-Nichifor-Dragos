import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CarService } from 'src/app/core/service/car.service';
import { CarOne } from 'src/app/core/model/car.model';

@Component({
  selector: 'app-car-details',
  templateUrl: './car-details.component.html',
  styleUrls: ['./car-details.component.css']
})
export class CarDetailsComponent {

  car?: CarOne
  carId?: string

  constructor(
    private carService: CarService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.carId = params['id']
      this.carService.getCar(this.carId!).subscribe((car: CarOne) => {
        this.car = car;
      })
    });
  }

  onBackToRacePage() {
    this.router.navigateByUrl("car-component")
  }


}
