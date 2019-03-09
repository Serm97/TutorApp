import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  getUsersRegister() {  return this.http.get('https://randomuser.me/api/?results=25');}
}
