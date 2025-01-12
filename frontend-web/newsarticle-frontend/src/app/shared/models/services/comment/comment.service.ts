import { inject, Injectable } from '@angular/core';
import { UserService } from '../user/user.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';
import { Comment } from '../../comment/comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  baseApiUrl: string = 'http://localhost:8083/comment/api/comment';
  httpClient: HttpClient = inject(HttpClient);
  userService: UserService = inject(UserService);

  private commentAddedSubject = new Subject<void>(); // Subject om events te triggeren
  commentAdded$ = this.commentAddedSubject.asObservable(); // Observable voor componenten

  constructor() { }

  getCommentsByNewsArticleId(id: number): Observable<Comment[]> {
    const headers = new HttpHeaders({newsArticleId: id});

    return this.httpClient.get<Comment[]>(this.baseApiUrl + '/ofnewsarticle', { headers });
  }

  addComment(comment: Comment): Observable<Comment> {
    return this.httpClient.post<Comment>(this.baseApiUrl, comment).pipe(
      tap(() => this.commentAddedSubject.next()) // Trigger event na succesvolle create
    );
  }

  updateComment(commentId: number, updatedComment: { usernameCommenter: string; content: string }): Observable<void> {
    return this.httpClient.put<void>(`${this.baseApiUrl}/updateComment/${commentId}`, updatedComment);
  }
  
  deleteComment(commentId: number, payload: { usernameCommenter: string }): Observable<void> {
    return this.httpClient.request<void>('delete', `${this.baseApiUrl}/deleteComment/${commentId}`, {
      body: payload,
    });
  }
}
