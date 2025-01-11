import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from '../user/user.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private baseUrl: string = 'http://localhost:8083/review/api/review';
  httpClient: HttpClient = inject(HttpClient);
  userService: UserService = inject(UserService);

  

  approveNewsArticle(newsArticleId: number, remark: string): Observable<void> {
    const usernameEditor = this.userService.getUsername();
    const body = {
      newsArticleId,
      usernameEditor,
      remark
    };
    return this.httpClient.post<void>(`${this.baseUrl}/approve`, body);
  }

  disapproveNewsArticle(newsArticleId: number, remark: string): Observable<void> {
    const usernameEditor = this.userService.getUsername();
    const body = {
      newsArticleId,
      usernameEditor,
      remark
    };
    return this.httpClient.post<void>(`${this.baseUrl}/disapprove`, body);
  }
  
}
