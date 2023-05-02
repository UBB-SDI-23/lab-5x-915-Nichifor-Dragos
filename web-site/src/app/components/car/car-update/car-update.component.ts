import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { CarAddUpdate, CarOne } from 'src/app/core/model/car.model';
import { User } from 'src/app/core/model/user.model';

import { ToastrService } from 'ngx-toastr';
import { CarService } from 'src/app/core/service/car.service';
import { StorageService } from 'src/app/core/service/storage.service';

@Component({
  selector: 'app-car-update',
  templateUrl: './car-update.component.html',
  styleUrls: ['./car-update.component.css']
})
export class CarUpdateComponent {

  car?: CarOne
  carId?: string
  carUpdateDTO?: CarAddUpdate
  currentUser?: User

  submitted = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private carService: CarService,
    private toastrService: ToastrService,
    private storageService: StorageService
  ) {}

  ngOnInit(): void {
    if(!this.storageService.isLoggedIn()) {
      this.toastrService.error("Logging in is required.", '', { progressBar: true }); this.onBackToHomePage();
    }
    this.currentUser = this.storageService.getUser()
    this.activatedRoute.params.subscribe(params => {
      this.carId = params['id']
      this.carService.getCar(this.carId!).subscribe(
        (car: CarOne) => {
          this.car = car;
          if (this.car.username != this.currentUser?.username && !this.currentUser?.roles.includes("ROLE_ADMIN") && !this.currentUser?.roles.includes("ROLE_MODERATOR")){
            this.toastrService.error("Can not edit this", '', { progressBar: true }); this.onBackToHomePage(); 
          }}, 
        (error) => { this.toastrService.error("Car does not exist", '', { progressBar: true }); this.onBackToHomePage() });
    })
  }

  onSubmit() { 
    this.submitted = true; 
    if (this.car) {
      this.carUpdateDTO = new CarAddUpdate(this.car.brand, this.car.motorization, this.car.gearBox, this.car.cylindricalCapacity, this.car.horsePower, this.car.description)
      this.carService.updateCar(this.carUpdateDTO, this.car.id).subscribe(
        (response) => { this.toastrService.success("Car updated successfully", '', { progressBar: true }); this.onBackToCarPage() },
        (error) => { this.toastrService.error("Could not update car", '', { progressBar: true }); this.onBackToCarPage() });
    }
  }

  onBackToCarPage() {
    this.router.navigate(['/car-component'], { queryParams: { pageNo: 0, pageSize: 25 } });
  }

  onBackToHomePage() {
    this.router.navigate(['/home-page'])
  }

}
