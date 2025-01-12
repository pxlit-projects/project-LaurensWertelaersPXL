import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Comment } from '../../../shared/models/comment/comment.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CommentService } from '../../../shared/models/services/comment/comment.service';
import { UserService } from '../../../shared/models/services/user/user.service';

@Component({
  selector: 'app-comment-item',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './comment-item.component.html',
  styleUrl: './comment-item.component.css'
})
export class CommentItemComponent {
  @Input() comment!: Comment;
  @Output() commentDeleted = new EventEmitter<number>(); // Emit het ID van de verwijderde comment

  isEditing: boolean = false;
  updatedContent: string = '';
  commentService: CommentService = inject(CommentService);
  userService: UserService = inject(UserService);

  startEditing(): void {
    this.isEditing = true;
    this.updatedContent = this.comment.content; // Laad de huidige content
  }

  cancelEditing(): void {
    this.isEditing = false;
    this.updatedContent = ''; // Reset de input
  }

  updateComment(): void {
    const updatedComment = {
      usernameCommenter: this.comment.usernameCommenter,
      content: this.updatedContent,
    };

    this.commentService
      .updateComment(this.comment.id!, updatedComment)
      .subscribe({
        next: () => {
          this.comment.content = this.updatedContent; // Werk lokaal bij
          this.isEditing = false; // Sluit de bewerkmodus
        },
        error: (err) => {
          console.error('Error bij updaten van de reactie:', err);
        },
      });
  }

  deleteComment(): void {
    const deletePayload = {
      usernameCommenter: this.userService.getUsername(),
    };

    this.commentService.deleteComment(this.comment.id!, deletePayload).subscribe({
      next: () => {
        console.log('Comment succesvol verwijderd');
        this.commentDeleted.emit(this.comment.id!); // Informeer de parent dat de comment is verwijderd

      },
      error: (err) => {
        console.error('Error bij verwijderen van de reactie:', err);
      },
    });
  }

  // Getter om te controleren of de huidige gebruiker de eigenaar is van de comment
  get isCommentOwner(): boolean {
    return this.comment.usernameCommenter === this.userService.getUsername();
  }
}
