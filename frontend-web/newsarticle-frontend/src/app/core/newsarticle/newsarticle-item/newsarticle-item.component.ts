import { Component, inject, Input } from '@angular/core';
import { NewsArticle } from '../../../shared/models/newsarticle/newsarticle.model';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { NewsarticleService } from '../../../shared/models/services/newsarticle/newsarticle.service';

@Component({
  selector: 'app-newsarticle-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './newsarticle-item.component.html',
  styleUrl: './newsarticle-item.component.css'
})
export class NewsarticleItemComponent {
  @Input() newsArticle!: NewsArticle;
  
  router: Router = inject(Router);
  newsArticleService: NewsarticleService = inject(NewsarticleService);

  
  editArticle(id: number): void {
    this.router.navigate([`/edit-article/${id}`]);
  }

  submitForApproval(id: number): void {
    this.newsArticleService.submitForApproval(id).subscribe({
      next: () => {
        // Mogelijk de status van het artikel bijwerken of een notificatie tonen
        this.newsArticle.status = 'SUBMITTED';  // Wijzig de status lokaal na succesvolle aanvraag
      },
      error: (err) => {
        console.error('Error submitting article for approval:', err);
      }
    });
  }
}
