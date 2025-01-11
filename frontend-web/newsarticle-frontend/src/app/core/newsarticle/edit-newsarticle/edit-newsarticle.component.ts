import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NewsArticle } from '../../../shared/models/newsarticle/newsarticle.model';
import { NewsarticleService } from '../../../shared/models/services/newsarticle/newsarticle.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../shared/models/services/user/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-newsarticle',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './edit-newsarticle.component.html',
  styleUrl: './edit-newsarticle.component.css'
})
export class EditNewsarticleComponent implements OnInit {
  newsArticleForm: FormGroup;
  articleId?: number;
  article: NewsArticle | undefined;

  constructor(
    private fb: FormBuilder,
    private newsArticleService: NewsarticleService,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {
    // Form initialisatie
    this.newsArticleForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(100)]],
      content: ['', [Validators.required]],
      usernameWriter: [''] 
    });
  }

  ngOnInit(): void {
    // Verkrijg het artikel ID uit de route
    this.articleId = +this.route.snapshot.paramMap.get('id')!;
    this.loadArticle();
  }

  // Haal het artikel op via de service
  loadArticle(): void {
    this.newsArticleService.getArticleById(this.articleId!).subscribe((article) => {
      this.article = article;
      this.newsArticleForm.patchValue({
        title: article.title,
        content: article.content,
        usernameWriter: article.usernameWriter,
      });
    });
  }

  onSubmit(): void {
    if (this.newsArticleForm.valid) {
      const updatedArticle = { ...this.article, ...this.newsArticleForm.value };
      this.newsArticleService.updateArticle(updatedArticle).subscribe({
        next: () => {
          this.router.navigate(['/writer']); // Redirect naar de lijst van artikelen
        },
        error: (err) => {
          console.error('Error updating article:', err);
        },
      });
    }
  }
}
