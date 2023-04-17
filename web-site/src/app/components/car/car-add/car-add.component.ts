import { Component } from '@angular/core';
import {debounceTime, distinctUntilChanged, filter, fromEvent, map, of, Subject} from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Pilot } from 'src/app/core/model/pilot.model';
import { PilotService } from 'src/app/core/service/pilot.service';
import { CarService } from 'src/app/core/service/car.service';
import { Router } from '@angular/router';
import { CarAddUpdate } from 'src/app/core/model/car.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-car-add',
  templateUrl: './car-add.component.html',
  styleUrls: ['./car-add.component.css']
})
export class CarAddComponent {

  submitted: boolean = false;

  brand?: string;
  motorization?: string;
  gearBox?: string;
  cylindricalCapacity?: number;
  horsePower?: number;
  description?: string;
  pilotID?: string;

  selectedOption?: string;
  selectedPilot?: Pilot;
  searchTerm = new Subject<string>();
  options?: Pilot[];

  constructor(
    private carService: CarService,
    private pilotService: PilotService,
    private router: Router,
    private toastrService: ToastrService
    ) {}

  ngOnInit() {
    this.searchTerm.pipe(
      debounceTime(1000)).subscribe(term => {
        if (term.trim()) {
          this.pilotService.getPilotsByName(term).subscribe({
            next: (pilots: Pilot[]) => {
              this.options = pilots; }});
        } else { this.options = undefined; }
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.brand && this.motorization && this.gearBox && this.cylindricalCapacity && this.horsePower && this.description) {
      const car: CarAddUpdate = {
        brand: this.brand,
        motorization: this.motorization,
        gearBox: this.gearBox,
        cylindricalCapacity: this.cylindricalCapacity,
        horsePower: this.horsePower,
        description: this.description
      }
      
      this.pilotID = this.selectedPilot?.id.toString();

      this.carService.addCar(car, this.pilotID!).subscribe(
        (response) => { this.toastrService.success("Car added successfully", '', { progressBar: true }); this.onBackToCarPage() },
        (error) => { this.toastrService.error("Could not add car", '', { progressBar: true }); this.onBackToCarPage() });

    this.onBackToCarPage()

    }
    else { this.toastrService.error("Could not add car", '', { progressBar: true }); this.onBackToCarPage() }
  }

  onSelection(event: any): void {
    console.log(event)
    this.selectedOption = event.option.value.firstName + " " + event.option.value.lastName;
    this.selectedPilot = event.option.value;
  }

  search(term: string): void {
    this.searchTerm.next(term);
  }

  onBackToCarPage() {
    this.router.navigate(['/car-component'])
  }

}
