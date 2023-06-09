import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

// const AUTH_API = 'http://localhost:8080/api/auth/';
const AUTH_API = 'https://racemasters.minecraftnoob.com/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(
      AUTH_API + 'signin',
      {
        username,
        password,
      },
      httpOptions
    );
  }

  register(username: string, password: string): Observable<any> {
    return this.http.post(
      AUTH_API + 'register',
      {
        username,
        password,
      },
      httpOptions
    );
  }

  confirmRegistration(token: string): Observable<any> {
    console.log(AUTH_API + 'register/confirm/' + token)
    return this.http.post(AUTH_API + 'register/confirm/' + token, httpOptions);
  }

  logout(): Observable<any> {
    return this.http.post(AUTH_API + 'signout', { }, httpOptions);
  }
}