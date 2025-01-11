import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private username: string = "";
  private role: string = "";
  
  constructor() { }

  setUserData(username: string, role: string) {
    this.username = username;
    this.role = role;
  }

  getUsername(): string{
    return this.username;
  }

  getRole(): string {
    return this.role;
  }
}
