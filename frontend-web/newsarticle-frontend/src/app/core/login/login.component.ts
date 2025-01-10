import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../shared/models/services/user.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  fb: FormBuilder = inject(FormBuilder);
  router: Router = inject(Router);
  userService: UserService = inject(UserService);

  loginForm: FormGroup = this.fb.group({
    username: ['', Validators.required],
    role: ['', Validators.required]
  });

  onSubmit() {
    if (this.loginForm.valid){
      const {username, role} = this.loginForm.value;

      //opslaan in localstorage
      this.userService.setUserData(username, role);

      //doorsturen naar andere pagina afhankelijk van de rol
      switch (role) {
        case 'writer':
          this.router.navigate(['/writer']);
          break;
        case 'editor':
          this.router.navigate(['/editor']);
          break;
        case 'member':
          this.router.navigate(['/member']);
          break;
        default:
          console.error('Onbekende rol:', role);
      }
    } else {
      console.error('Formulier is ongeldig!');
    }
  }
}
