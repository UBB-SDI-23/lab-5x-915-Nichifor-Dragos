import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CarAddUpdate, CarOne } from 'src/app/core/model/car.model';
import { CarService } from 'src/app/core/service/car.service';

@Component({
  selector: 'app-car-update',
  templateUrl: './car-update.component.html',
  styleUrls: ['./car-update.component.css']
})
export class CarUpdateComponent {

  car?: CarOne
  carId?: string
  carUpdateDTO?: CarAddUpdate

  submitted = false;

  constructor(
    private carService: CarService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.carId = params['id']
      this.carService.getCar(this.carId!).subscribe((car: CarOne) => {
        this.car = car;
      })
    });
  }

  onSubmit() { 
    this.submitted = true; 
    if (this.car) {
      this.carUpdateDTO = new CarAddUpdate(this.car.brand, this.car.motorization, this.car.gearBox, this.car.cylindricalCapacity, this.car.horsePower, this.car.description)
      this.carService.updateCar(this.carUpdateDTO, this.car.id).subscribe(
        (response) => { this.toastrService.success("Car updated successfully", '', { progressBar: true }) },
        (error) => { this.toastrService.error("Could not update car", '', { progressBar: true }) });
    }
    this.onBackToCarPage()
  }

  onBackToCarPage() {
    this.router.navigate(['/car-component'])
  }

}
