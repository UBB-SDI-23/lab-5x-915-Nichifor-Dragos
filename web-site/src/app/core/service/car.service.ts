import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable, Subscriber } from "rxjs";
import { Car, CarOne, CarAddUpdate } from "../model/car.model";
import { Pilot } from "../model/pilot.model";

@Injectable()
export class CarService {

    // private baseUrl = "http://localhost:8080/api/";
    private baseUrl = "https://racemasters.strangled.net/api/";
    
    constructor(private httpClient: HttpClient) { }

    listCars(): Observable<Car[]> {
        return this.httpClient.get<Car[]>(this.baseUrl + 'car');
    }

    listCarsWithCP(cylindricalCapacity: string): Observable<Car[]> {
        return this.httpClient.get<Car[]>(this.baseUrl + 'car?capacity=' + cylindricalCapacity);
    }

    listPageCars(pageNo: Number, pageSize: Number, cylindricalCapacity ?: string): Observable<Car[]> {
      if (cylindricalCapacity != undefined)
        return this.httpClient.get(this.baseUrl + "car?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString() + '&capacity=' + cylindricalCapacity) as Observable<Car[]>;
      else
        return this.httpClient.get(this.baseUrl + "car?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString()) as Observable<Car[]>;
    }

    countCars(): Observable<Number> {
      return this.httpClient.get(this.baseUrl + "car/count") as Observable<Number>;
    }

    countCarsCapacity(capacity: string): Observable<Number> {
      return this.httpClient.get(this.baseUrl + "car/count-capacity?capacity=" + capacity) as Observable<Number>;
    }

    getCar(id: string): Observable<CarOne>
    {
      return this.httpClient.get(this.baseUrl + "car/" + id) as Observable<CarOne>;
    }
  
    addCar(car: CarAddUpdate, pilotId: string): Observable<CarAddUpdate> {
      return this.httpClient.post(this.baseUrl + "pilot/" + pilotId + "/car", car) as Observable<CarAddUpdate>
    }

    updateCar(car: CarAddUpdate, id: string) {
      return this.httpClient.put(this.baseUrl + "car/" + id, car)
    }

    deleteCar(id: string) {
      return this.httpClient.delete(this.baseUrl + "car/" + id)
    }

}