import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { NewsArticle } from '../../../shared/models/newsarticle/newsarticle.model';
import { Subscription } from 'rxjs';
import { NewsarticleService } from '../../../shared/models/services/newsarticle/newsarticle.service';
import { NewsarticleItemComponent } from '../newsarticle-item/newsarticle-item.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UserService } from '../../../shared/models/services/user/user.service';
import { FilterComponent } from '../../filter/filter.component';

@Component({
  selector: 'app-newsarticle-list',
  standalone: true,
  imports: [NewsarticleItemComponent, CommonModule, FilterComponent],
  templateUrl: './newsarticle-list.component.html',
  styleUrl: './newsarticle-list.component.css'
})
export class NewsarticleListComponent implements OnInit, OnDestroy {
  myNewsArticles: NewsArticle[] | undefined;
  private subscription: Subscription | undefined;

  newsArticleService: NewsarticleService = inject(NewsarticleService);
  userService: UserService = inject(UserService);
  role: string = this.userService.getRole();

  filter: any = {};

  ngOnInit(): void {
    //newsarticles ophalen
    this.fetchArticles();

    // luisteren naar updates wanneer een newsarticle is aangemaakt
    this.newsArticleService.articleAdded$.subscribe(() => {
      this.fetchArticles();
    });
  }

  fetchArticles(): void {
    if (this.role === "writer") {
      this.subscription = this.newsArticleService.getMyNewsArticles().subscribe({
        next: (articles) => {
          this.myNewsArticles = articles;
        },
        error: (err) => {
          console.error('Error met fetchen van news articles: ', err);
        }
      });
    } else if (this.role === "editor"){
      this.newsArticleService.getNewsArticlesByStatus('SUBMITTED').subscribe({
        next: (articles) => {
          this.myNewsArticles = articles;
        },
        error: (err) => {
          console.error('Error met fetchen van news articles: ', err);
        }
      })
    } else if (this.role === "member"){
      this.newsArticleService.getNewsArticlesByStatus('APPROVED').subscribe({
        next: (articles) => {
          this.myNewsArticles = this.applyFilters(articles);
        },
        error: (err) => {
          console.error('Error met fetchen van news articles: ', err);
        }
      });
    }
  }

  // pas filters toe op newsarticles
  applyFilters(articles: NewsArticle[]): NewsArticle[] {
    return articles.filter(article => {
      const matchesContent = this.filter.content ? article.content.includes(this.filter.content) : true;
      const matchesAuthor = this.filter.usernameWriter ? article.usernameWriter.includes(this.filter.usernameWriter) : true;
      //const matchesDate = this.filter.creationDate ? new Date(article.creationDate).toISOString().split('T')[0] === this.filter.creationDate : true;
      const matchesDate = this.filter.creationDate ? new Date(article.creationDate) > new Date(this.filter.creationDate) : true;

      return matchesContent && matchesAuthor && matchesDate;
    });
  }

  // Handle filter veranderingen van de FilterComponent
  onFilterChanged(filter: any): void {
    this.filter = filter;
    this.fetchArticles();
  }

  ngOnDestroy(): void {
    //opschonen van de subscription
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }


}
