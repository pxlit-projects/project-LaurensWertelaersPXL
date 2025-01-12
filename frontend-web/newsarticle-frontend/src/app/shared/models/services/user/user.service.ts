import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private username: string = "";
  private role: string = "";
  
  constructor() {
    const storedUsername = localStorage.getItem('username');
    const storedRole = localStorage.getItem('role');

    if (storedUsername) {
      this.username = storedUsername;
    }

    if (storedRole) {
      this.role = storedRole;
    }
  }

  setUserData(username: string, role: string) {
    this.username = username;
    this.role = role;

    // Opslaan in localStorage
    localStorage.setItem('username', username);
    localStorage.setItem('role', role);
  }

  getUsername(): string{
    if (!this.username) {
      // Probeer uit localStorage te halen als de interne property leeg is
      const storedUsername = localStorage.getItem('username');
      if (storedUsername) {
        this.username = storedUsername;
      }
    }
    return this.username;
  }

  getRole(): string {
    if (!this.role) {
      // Probeer uit localStorage te halen als de interne property leeg is
      const storedRole = localStorage.getItem('role');
      if (storedRole) {
        this.role = storedRole;
      }
    }
    return this.role;
  }

  logout() {
    // Verwijder gegevens uit localStorage
    localStorage.removeItem('username');
    localStorage.removeItem('role');

    // Reset interne properties
    this.username = "";
    this.role = "";
  }
}
