import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { Pilot } from "../model/pilot.model";

@Injectable()
export class PilotService {
    private baseUrl = "http://localhost/api";
    
    constructor(private httpClient: HttpClient) { }

    listRaces(): Observable<Pilot[]> {
        return this.httpClient.get<Pilot[]>(this.baseUrl + '/pilot');
    }
}