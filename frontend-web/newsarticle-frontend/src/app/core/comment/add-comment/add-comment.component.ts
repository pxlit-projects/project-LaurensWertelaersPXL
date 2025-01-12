import { Component, inject, Input } from '@angular/core';
import { CommentService } from '../../../shared/models/services/comment/comment.service';
import { UserService } from '../../../shared/models/services/user/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-comment',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-comment.component.html',
  styleUrl: './add-comment.component.css'
})
export class AddCommentComponent {
  @Input() articleId!: number;
  newCommentContent: string = '';
  commentService: CommentService = inject(CommentService);
  userService: UserService = inject(UserService);

  addComment(): void {
    if (this.newCommentContent.trim()) {
      const newComment = {
        newsArticleId: this.articleId,
        usernameCommenter: this.userService.getUsername(),
        content: this.newCommentContent,
        creationDate: new Date()
      };

      this.commentService.addComment(newComment).subscribe({
        next: () => {
          this.newCommentContent = ''; // Reset het formulier
        },
        error: (err) => {
          console.error('Error adding comment:', err);
        }
      });
    }
  }
}
