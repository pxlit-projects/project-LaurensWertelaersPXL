import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from '../user/user.service';
import { Review } from '../../review/review.model';
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  baseApiUrl: string = environment.apiUrl + '/review/api/review';
  //baseUrl: string = 'http://localhost:8083/review/api/review';
  httpClient: HttpClient = inject(HttpClient);
  userService: UserService = inject(UserService);

  

  approveNewsArticle(newsArticleId: number, remark: string): Observable<void> {
    const userNameEditor = this.userService.getUsername();
    console.log(userNameEditor);
    const body = {
      newsArticleId,
      userNameEditor,
      remark
    };

    console.log(body);
    return this.httpClient.post<void>(`${this.baseApiUrl}/approve`, body);
  }

  disapproveNewsArticle(newsArticleId: number, remark: string): Observable<void> {
    const userNameEditor = this.userService.getUsername();
    const body = {
      newsArticleId,
      userNameEditor,
      remark
    };
    return this.httpClient.post<void>(`${this.baseApiUrl}/disapprove`, body);
  }

  getReviewByNewsArticleId(newsArticleId: number): Observable<Review> {
    const headers = new HttpHeaders({ newsArticleId: newsArticleId.toString() });

    return this.httpClient.get<Review>(this.baseApiUrl + "/ofnewsarticle", { headers });
  }
  
}
