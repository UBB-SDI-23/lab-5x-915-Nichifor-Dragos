import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { Car, CarOne, CarAddUpdate } from "../model/car.model";

@Injectable()
export class CarService {
    // /api/* http://54.82.120.234/api/:splat 200!
    // /* /index.html 200`

    // private baseUrl = "/api/";
    private baseUrl = "http://localhost/api/"
    
    constructor(private httpClient: HttpClient) { }

    listCars(): Observable<Car[]> {
        return this.httpClient.get<Car[]>(this.baseUrl + 'car');
    }

    listCarsWithCP(cylindricalCapacity: string): Observable<Car[]> {
        return this.httpClient.get<Car[]>(this.baseUrl + 'car?capacity=' + cylindricalCapacity);
    }

    getCar(id: string): Observable<CarOne>
    {
      return this.httpClient.get(this.baseUrl + "car/" + id) as Observable<CarOne>;
    }
  
    addCar(car: CarAddUpdate): Observable<CarAddUpdate> {
      return this.httpClient.post(this.baseUrl + "car", car) as Observable<CarAddUpdate>
    }

    updateCar(car: CarAddUpdate, id: string) {
      return this.httpClient.put(this.baseUrl + "car/" + id, car)
    }

    deleteCar(id: string) {
      return this.httpClient.delete(this.baseUrl + "car/" + id)
    }

}