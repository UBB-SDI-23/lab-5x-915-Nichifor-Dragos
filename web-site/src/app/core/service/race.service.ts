import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { Race } from "../model/race.model";

@Injectable()
export class RaceService {
    private baseUrl = "http://localhost/api";
    
    constructor(private httpClient: HttpClient) { }

    listRaces(): Observable<Race[]> {
        return this.httpClient.get<Race[]>(this.baseUrl + '/race');
    }

    // saveRace(raceInput: raceInput): Observable<ValidationError[]> {
    //     return this.httpClient.post<ValidationError[]>(this.baseUrl + '/race', raceInput);
    // }

    // deleteRace(articleID: string): Observable<void> {
    //     return this.httpClient.delete<void>(this.baseUrl + /race + `articleID`, null);
    // }

}