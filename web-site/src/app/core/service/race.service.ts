import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { Race, RaceAddUpdate, RaceOne } from "../model/race.model";

@Injectable()
export class RaceService {
    private baseUrl = "http://localhost/api/";
    
    constructor(private httpClient: HttpClient) { }

    listRaces(): Observable<Race[]> {
        return this.httpClient.get<Race[]>(this.baseUrl + 'race');
    }

    getRace(id: string): Observable<RaceOne>
    {
      return this.httpClient.get(this.baseUrl + "race/" + id) as Observable<RaceOne>;
    }
  
    addRace(race: RaceAddUpdate): Observable<Race> { // make it work
      console.log(race)
      return this.httpClient.post(this.baseUrl + "race", race) as Observable<Race>
    }

    updateRace(race: RaceAddUpdate, id: string) {
      console.log(race, id)
    }

    deleteRace(id: string) {
      console.log("delete" + id)
      console.log(this.baseUrl + "race/" + id)
      return this.httpClient.delete(this.baseUrl + "race/" + id)
    }

}