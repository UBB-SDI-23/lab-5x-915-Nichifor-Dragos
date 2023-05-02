import { Injectable } from "@angular/core";
import { User, UserProfile, UserProfileUpdate } from "../model/user.model";

import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";

@Injectable()
export class UserService {
    // private baseUrl = "/api/";
    private baseUrl = "http://localhost/api/";

    constructor(private httpClient: HttpClient) { }

    getUserProfileById(userId: string): Observable<UserProfile> {
      return this.httpClient.get(this.baseUrl + "user-profile-id/" + userId) as Observable<UserProfile>;
    }
    
    getUserProfileByUsername(username: string): Observable<UserProfile> {
      return this.httpClient.get(this.baseUrl + "user-profile-username/" + username) as Observable<UserProfile>;
    }
    
    getUserByUsername(username: string): Observable<User> {
      return this.httpClient.get(this.baseUrl + "user/" + username) as Observable<User>;
    }

    getNumberOfCars(id: string): Observable<Number> {
      return this.httpClient.get(this.baseUrl + "user-number-cars/" + id) as Observable<Number>;
    }

    getNumberOfPilots(id: string): Observable<Number> {
      return this.httpClient.get(this.baseUrl + "user-number-pilots/" + id) as Observable<Number>;
    }  

    getNumberOfRaces(id: string): Observable<Number> {
      return this.httpClient.get(this.baseUrl + "user-number-races/" + id) as Observable<Number>;
    }
    
    updateUserProfile(race: UserProfileUpdate, id: string) {
      return this.httpClient.put(this.baseUrl + "user-profile/" + id, race)
    }

}