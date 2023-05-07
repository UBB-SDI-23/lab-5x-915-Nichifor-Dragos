import { Injectable } from "@angular/core";
import { SQLResponse } from "../model/user.model";

import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";

@Injectable()
export class SqlService {

    // private baseUrl = "http://localhost/api/";
    private baseUrl = "https://racemasters.jumpingcrab.com/api/";

    constructor(private httpClient: HttpClient) { }

    insertAllRaces(): Observable<SQLResponse> {
        return this.httpClient.post(this.baseUrl + "run-insert-races-script", {}) as Observable<SQLResponse>;
      }

    deleteAllRaces(): Observable<SQLResponse> {
      return this.httpClient.post(this.baseUrl + "run-delete-races-script", {}) as Observable<SQLResponse>;
    }

    insertAllPilots(): Observable<SQLResponse> {
        return this.httpClient.post(this.baseUrl + "run-insert-pilots-script", {}) as Observable<SQLResponse>;
    }
  
    deleteAllPilots(): Observable<SQLResponse> {
      return this.httpClient.post(this.baseUrl + "run-delete-pilots-script", {}) as Observable<SQLResponse>;
    }
  
    insertAllCars(): Observable<SQLResponse> {
        return this.httpClient.post(this.baseUrl + "run-insert-cars-script", {}) as Observable<SQLResponse>;
    }

    deleteAllCars(): Observable<SQLResponse> {
      return this.httpClient.post(this.baseUrl + "run-delete-cars-script", {}) as Observable<SQLResponse>;
    }
      
    insertAllParticipations(): Observable<SQLResponse> {
        return this.httpClient.post(this.baseUrl + "run-insert-participations-script", {}) as Observable<SQLResponse>;
      }

    deleteAllParticipations(): Observable<SQLResponse> {
      return this.httpClient.post(this.baseUrl + "run-delete-participations-script", {}) as Observable<SQLResponse>;
    }

}