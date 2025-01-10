import { Component, inject } from '@angular/core';
import { UserService } from '../../shared/models/services/user.service';

@Component({
  selector: 'app-writer',
  standalone: true,
  imports: [],
  templateUrl: './writer.component.html',
  styleUrl: './writer.component.css'
})
export class WriterComponent {
  userService: UserService = inject(UserService);

  role = this.userService.getRole();
  username = this.userService.getUsername();

}
