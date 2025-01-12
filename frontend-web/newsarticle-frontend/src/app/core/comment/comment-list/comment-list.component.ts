import { Component, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CommentService } from '../../../shared/models/services/comment/comment.service';
import { Comment } from '../../../shared/models/comment/comment.model';
import { CommentItemComponent } from '../comment-item/comment-item.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-comment-list',
  standalone: true,
  imports: [CommentItemComponent, CommonModule],
  templateUrl: './comment-list.component.html',
  styleUrl: './comment-list.component.css'
})
export class CommentListComponent implements OnInit, OnDestroy {
  @Input() articleId!: number;
  comments: Comment[] = [];
  private subscriptions: Subscription[] = [];
  commentService: CommentService = inject(CommentService);

  ngOnInit(): void {
    this.fetchComments();

    // Luister naar updates wanneer een nieuwe comment wordt toegevoegd
    const commentAddedSub = this.commentService.commentAdded$.subscribe(() => {
      this.fetchComments();
    });

    this.subscriptions.push(commentAddedSub);
  }

  fetchComments(): void {
    this.commentService.getCommentsByNewsArticleId(this.articleId).subscribe({
      next: (comments) => {
        this.comments = comments;
      },
      error: (err) => {
        console.error('Error fetching comments:', err);
      }
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  onCommentDeleted(commentId: number): void {
    this.fetchComments();
  }
}
