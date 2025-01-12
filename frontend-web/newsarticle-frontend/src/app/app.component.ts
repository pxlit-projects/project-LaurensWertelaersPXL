import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { UserService } from './shared/models/services/user/user.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  userService: UserService = inject(UserService);
  router: Router = inject(Router);

  onLogout() {
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
