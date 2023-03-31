import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { Car } from "../model/car.model";

@Injectable()
export class CarService {
    private baseUrl = "http://localhost/api";
    
    constructor(private httpClient: HttpClient) { }

    listCars(): Observable<Car[]> {
        return this.httpClient.get<Car[]>(this.baseUrl + '/car');
    }

}