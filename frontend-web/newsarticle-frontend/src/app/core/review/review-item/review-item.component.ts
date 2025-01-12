import { Component, inject, Input, OnInit } from '@angular/core';
import { Review } from '../../../shared/models/review/review.model';
import { ReviewService } from '../../../shared/models/services/review/review.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-review-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './review-item.component.html',
  styleUrl: './review-item.component.css'
})
export class ReviewItemComponent implements OnInit {
  @Input() newsArticleId!: number;
  review: Review | null = null;
  errorMessage: string | null = null;
  reviewService: ReviewService = inject(ReviewService);

  ngOnInit(): void {
    this.fetchReview();
  }

  fetchReview(): void {
    this.reviewService.getReviewByNewsArticleId(this.newsArticleId).subscribe({
      next: (review) => {
        this.review = review; // Wijs de opgehaalde review toe
      },
      error: (err) => {
        this.errorMessage = 'Geen review';
        console.error('Error fetching review:', err);
      }
    });
  }
}
