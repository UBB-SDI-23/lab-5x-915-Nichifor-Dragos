import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { Race, RaceAdd } from "../model/race.model";

@Injectable()
export class RaceService {
    private baseUrl = "http://localhost/api";
    
    constructor(private httpClient: HttpClient) { }

    listRaces(): Observable<Race[]> {
        return this.httpClient.get<Race[]>(this.baseUrl + '/race');
    }

    getRace(raceId: string): Observable<Race>
    {
      return this.httpClient.get(this.baseUrl + "/race" + raceId) as Observable<Race>;
    }
  
    addRace(race: RaceAdd): Observable<Race> {
      return this.httpClient.post(this.baseUrl + "/race", race) as Observable<Race>
    }
}