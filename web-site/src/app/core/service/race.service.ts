import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";
import { ParticipationAdd, Race, RaceAddUpdate, RaceOne, RaceStatisticDTO, RaceAll } from "../model/race.model";

@Injectable()
export class RaceService {

    private baseUrl = "http://localhost:8080/api/";
    // private baseUrl = "https://racemasters.minecraftnoob.com/api/";
    
    constructor(private httpClient: HttpClient) { }

    listRaces(): Observable<RaceAll[]> {
        return this.httpClient.get<RaceAll[]>(this.baseUrl + 'race');
    }

    listPageRaces(pageNo: Number, pageSize: Number): Observable<RaceAll[]> {
      return this.httpClient.get(this.baseUrl + "race?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString()) as Observable<RaceAll[]>;
    }

    listRaceStatistic(): Observable<RaceStatisticDTO[]> {
      return this.httpClient.get<RaceStatisticDTO[]>(this.baseUrl + 'race/pilots-statistic')
    }

    countRaces(): Observable<Number> {
      return this.httpClient.get(this.baseUrl + "race/count") as Observable<Number>;
    }

    getRace(id: string): Observable<RaceOne> {
      return this.httpClient.get(this.baseUrl + "race/" + id) as Observable<RaceOne>;
    }

    getCountParticipants(id: string): Observable<Number> {
      return this.httpClient.get(this.baseUrl + "race/no-participants/" + id) as Observable<Number>;
    }
  
    addRace(race: RaceAddUpdate): Observable<Race> {
      return this.httpClient.post(this.baseUrl + "race", race) as Observable<Race>
    }

    updateRace(race: RaceAddUpdate, id: string) {
      return this.httpClient.put(this.baseUrl + "race/" + id, race)
    }

    deleteRace(id: string) {
      return this.httpClient.delete(this.baseUrl + "race/" + id)
    }

    getParticipation(pilotId: string, raceId: string): Observable<ParticipationAdd> {
      return this.httpClient.get(this.baseUrl + "races/" + raceId + "/pilots/" + pilotId, {}) as Observable<ParticipationAdd>
    }

    addParticipation(participation: ParticipationAdd, pilotId: string, raceId: string): Observable<ParticipationAdd> {
      return this.httpClient.post(this.baseUrl + "races/" + raceId + "/pilots/" + pilotId, participation, {}) as Observable<ParticipationAdd>
    }

    updateParticipation(participation: ParticipationAdd, pilotId: string, raceId: string): Observable<ParticipationAdd> {
      return this.httpClient.put(this.baseUrl + "races/" + raceId + "/pilots/" + pilotId, participation, {}) as Observable<ParticipationAdd>
    }

    deleteParticipation(raceId: string, pilotId: string) {
      return this.httpClient.delete(this.baseUrl + "races/" + raceId + "/pilots/" + pilotId, {})
    }

}