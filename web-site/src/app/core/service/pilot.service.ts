import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { Pilot, PilotOne, PilotAddUpdate } from "../model/pilot.model";

@Injectable()
export class PilotService {
    // private baseUrl = "/api/";
    private baseUrl = "http://localhost/api/";
    
    constructor(private httpClient: HttpClient) { }

    listPilots(): Observable<Pilot[]> {
        return this.httpClient.get<Pilot[]>(this.baseUrl + 'pilot');
    }

    getPilot(id: string): Observable<PilotOne>
    {
      return this.httpClient.get(this.baseUrl + "pilot/" + id) as Observable<PilotOne>;
    }
  
    addPilot(pilot: PilotAddUpdate): Observable<PilotAddUpdate> {
      return this.httpClient.post(this.baseUrl + "pilot", pilot) as Observable<PilotAddUpdate>
    }

    updatePilot(pilot: PilotAddUpdate, id: string) {
      return this.httpClient.put(this.baseUrl + "pilot/" + id, pilot)
    }

    deletePilot(id: string) {
      return this.httpClient.delete(this.baseUrl + "pilot/" + id)
    }

}