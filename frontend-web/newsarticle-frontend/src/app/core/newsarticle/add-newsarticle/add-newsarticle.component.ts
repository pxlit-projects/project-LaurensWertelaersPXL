import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NewsarticleService } from '../../../shared/models/services/newsarticle/newsarticle.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../shared/models/services/user/user.service';

@Component({
  selector: 'app-add-newsarticle',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-newsarticle.component.html',
  styleUrl: './add-newsarticle.component.css'
})
export class AddNewsarticleComponent {
  fb: FormBuilder = inject(FormBuilder);
  newsArticleService: NewsarticleService = inject(NewsarticleService);
  userService: UserService = inject(UserService);
  router: Router = inject(Router);


  newsArticleForm: FormGroup = this.fb.group({
    title: ['', [Validators.required, Validators.maxLength(100)]],
    content: ['', [Validators.required]],
    usernameWriter: [''] //wordt vanzelf ingevuld
  })

  constructor() {
    //haal de username op uit usernameservice
    const username = this.userService.getUsername();
    this.newsArticleForm.patchValue({ usernameWriter: username });
  }

  onSubmit(): void {
    if (this.newsArticleForm.valid) {
      this.newsArticleService.createNewsArticle(this.newsArticleForm.value).subscribe({
        next: (response) => {

          // Reset het formulier, behoud de waarde van usernameWriter
          this.newsArticleForm.reset({
            title: '',
            content: '',
            usernameWriter: this.userService.getUsername() // Stel de username opnieuw in
          });
        },
        error: (err) => {
          console.error('Error creating article:', err);
        }
      })
    }
  }
}
