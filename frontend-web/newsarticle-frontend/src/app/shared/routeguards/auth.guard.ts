import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { UserService } from '../models/services/user/user.service';


@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router){}

  canActivate(): boolean {
    const username = this.userService.getUsername();
    const role = this.userService.getRole();

    
    //controleer of beide username & role ingesteld zijn
    if (username && role) {
      return true; // sta toegang tot route toe
    } else {
      //redirect naar homepage (login page)
      this.router.navigate(['/']);
      return false;
    }
  }
}
