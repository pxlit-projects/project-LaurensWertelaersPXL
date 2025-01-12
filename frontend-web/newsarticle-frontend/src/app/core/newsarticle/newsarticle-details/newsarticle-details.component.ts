import { Component, inject, OnInit } from '@angular/core';
import { NewsArticle } from '../../../shared/models/newsarticle/newsarticle.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NewsarticleService } from '../../../shared/models/services/newsarticle/newsarticle.service';
import { CommentService } from '../../../shared/models/services/comment/comment.service';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../shared/models/services/user/user.service';
import { FormsModule } from '@angular/forms';
import { AddCommentComponent } from "../../comment/add-comment/add-comment.component";
import { CommentListComponent } from '../../comment/comment-list/comment-list.component';
import { ReviewItemComponent } from '../../review/review-item/review-item.component';

@Component({
  selector: 'app-newsarticle-details',
  standalone: true,
  imports: [CommonModule, FormsModule, AddCommentComponent, CommentListComponent, ReviewItemComponent],
  templateUrl: './newsarticle-details.component.html',
  styleUrl: './newsarticle-details.component.css'
})
export class NewsarticleDetailsComponent implements OnInit {
  newsArticle!: NewsArticle;
  comments: any[] = [];
  commentContent: string = '';
  role?: string;
  private route: ActivatedRoute = inject(ActivatedRoute);
  private router: Router = inject(Router);
  private newsArticleService: NewsarticleService = inject(NewsarticleService);
  userService: UserService = inject(UserService);
  articleId?: number;

  ngOnInit(): void {
    this.role = this.userService.getRole();

    this.articleId = Number(this.route.snapshot.paramMap.get('id'));

    // Ophalen van het artikel
    this.newsArticleService.getArticleById(this.articleId).subscribe({
      next: (article) => {
        this.newsArticle = article;
      },
      error: (err) => console.error('Error fetching article:', err),
    });
  }

  goBack(): void {
    if (this.role === "member") {
      this.router.navigate(['/member']);
    } else if (this.role === "writer"){
      this.router.navigate(['/writer']);
    } else if (this.role === "editor"){
      this.router.navigate(['/editor']);
    } 
  }
}
