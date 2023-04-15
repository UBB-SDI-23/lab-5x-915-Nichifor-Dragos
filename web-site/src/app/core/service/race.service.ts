import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { Race, RaceAddUpdate, RaceOne, RaceStatisticDTO } from "../model/race.model";

@Injectable()
export class RaceService {
    // private baseUrl = "/api/";
    private baseUrl = "http://localhost/api/";
    
    constructor(private httpClient: HttpClient) { }

    listRaces(): Observable<Race[]> {
        return this.httpClient.get<Race[]>(this.baseUrl + 'race');
    }

    listPageRaces(pageNo: Number, pageSize: Number): Observable<Race[]> {
      return this.httpClient.get(this.baseUrl + "race?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString()) as Observable<Race[]>;
    }

    listRaceStatistic(): Observable<RaceStatisticDTO[]> {
      return this.httpClient.get<RaceStatisticDTO[]>(this.baseUrl + 'race/pilots-statistic')
    }

    countRaces(): Observable<Number> {
      return this.httpClient.get(this.baseUrl + "race/count") as Observable<Number>;
    }

    getRace(id: string): Observable<RaceOne>
    {
      return this.httpClient.get(this.baseUrl + "race/" + id) as Observable<RaceOne>;
    }
  
    addRace(race: RaceAddUpdate): Observable<Race> {
      return this.httpClient.post(this.baseUrl + "race", race) as Observable<Race>
    }

    updateRace(race: RaceAddUpdate, id: string) {
      return this.httpClient.put(this.baseUrl + "race/" + id, race)
    }

    deleteRace(id: string) {
      // SET UP CASCADE -> THIS DOES NOT WORK
      return this.httpClient.delete(this.baseUrl + "race/" + id)
    }

}