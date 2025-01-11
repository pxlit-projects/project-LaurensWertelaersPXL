import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { UserService } from '../user/user.service';
import { Observable, Subject, tap } from 'rxjs';
import { NewsArticle } from '../../newsarticle/newsarticle.model';

@Injectable({
  providedIn: 'root'
})

export class NewsarticleService {
  baseApiUrl: string = 'http://localhost:8083/newsarticle/api/newsarticle';
  httpClient: HttpClient = inject(HttpClient);
  userService: UserService = inject(UserService);

  //om list van newsarticles bij te werken als een create gebeurd is.
  private articleAddedSubject = new Subject<void>(); // Subject om een gebeurtenis te triggeren wanneer een nieuw artikel is toegevoegd
  articleAdded$ = this.articleAddedSubject.asObservable(); // Expose als observable

  constructor() { }

  // get by id
  getArticleById(id: number): Observable<NewsArticle> {
    return this.httpClient.get<NewsArticle>(`${this.baseApiUrl}/${id}`);
  }

  //get by name
  getMyNewsArticles(): Observable<NewsArticle[]>{
    let headers = new HttpHeaders({
      usernameWriter: this.userService.getUsername()
    });

    return this.httpClient.get<NewsArticle[]>(this.baseApiUrl + '/myarticles', { headers});
  }

  //create
  createNewsArticle(article: Partial<NewsArticle>): Observable<NewsArticle> {
    return this.httpClient.post<NewsArticle>(this.baseApiUrl, article).pipe(
      tap(() => this.articleAddedSubject.next()) // trigger update
    );
  }

  // Update (saveAsConcept)
  updateArticle(article: NewsArticle): Observable<NewsArticle> {
    return this.httpClient.put<NewsArticle>(`${this.baseApiUrl}/saveAsConcept/${article.id}`, article);
  }

  // submit for approval
  submitForApproval(id: number): Observable<void> {
    const username = this.userService.getUsername();
    
    const body = {
      usernameWriter: username
    }

    return this.httpClient.put<void>(`${this.baseApiUrl}/submitForApproval/${id}`, body);
  }

  //getall maar alleen met gegeven status
  getNewsArticlesByStatus(status: string): Observable<NewsArticle[]> {
    return this.httpClient.get<NewsArticle[]>(`${this.baseApiUrl}/withstatus`, {
      headers: { status },
    });
  }
}
