import { Component, inject, Input } from '@angular/core';
import { NewsArticle } from '../../../shared/models/newsarticle/newsarticle.model';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { NewsarticleService } from '../../../shared/models/services/newsarticle/newsarticle.service';
import { UserService } from '../../../shared/models/services/user/user.service';
import { ReviewService } from '../../../shared/models/services/review/review.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-newsarticle-item',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './newsarticle-item.component.html',
  styleUrl: './newsarticle-item.component.css'
})
export class NewsarticleItemComponent {
  @Input() newsArticle!: NewsArticle;
  router: Router = inject(Router);
  userService: UserService = inject(UserService);
  newsArticleService: NewsarticleService = inject(NewsarticleService);
  reviewService: ReviewService = inject(ReviewService);
  role: string = this.userService.getRole();
  remark: string = '';
  isApproving: boolean = false;
  isDisApproving: boolean = false;

  
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
    

  confirmApprove(newsArticleId: number): void {  
    this.reviewService.approveNewsArticle(newsArticleId, this.remark).subscribe({
      next: () => {
        this.newsArticle.status = 'APPROVED'; // Update de status lokaal
        this.isApproving = false; // Reset isApproving
        this.isDisApproving = false;
        this.remark = ''; // Reset remark
      },
      error: (err) => {
        console.error('Error approving article:', err);
        this.isApproving = false;
        this.isDisApproving = false;
      }
    });
  }

  disapproveNewsArticle(newsArticleId: number): void {  
    this.reviewService.disapproveNewsArticle(newsArticleId, this.remark).subscribe({
      next: () => {
        this.newsArticle.status = 'DISAPPROVED'; // Update de status lokaal
        this.isApproving = false; // Reset isApproving
        this.isDisApproving = false; //reset isDisapproving
        this.remark = ''; // Reset remark
      },
      error: (err) => {
        console.error('Error disapproving article:', err);
        this.isApproving = false;
        this.isDisApproving = false;
      }
    });
  }

  goToDetails(id: number): void {
    this.router.navigate([`/article/${id}`]);
  }  
}
