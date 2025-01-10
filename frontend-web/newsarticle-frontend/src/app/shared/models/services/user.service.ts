import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private username: string | null = null;
  private role: string | null = null;
  
  constructor() { }

  setUserData(username: string, role: string) {
    this.username = username;
    this.role = role;
  }

  getUsername(): string | null {
    return this.username;
  }

  getRole(): string | null {
    return this.role;
  }
}
