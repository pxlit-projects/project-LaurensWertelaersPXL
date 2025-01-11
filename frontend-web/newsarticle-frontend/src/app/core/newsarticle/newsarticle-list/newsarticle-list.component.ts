import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { NewsArticle } from '../../../shared/models/newsarticle/newsarticle.model';
import { Subscription } from 'rxjs';
import { NewsarticleService } from '../../../shared/models/services/newsarticle/newsarticle.service';
import { NewsarticleItemComponent } from '../newsarticle-item/newsarticle-item.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-newsarticle-list',
  standalone: true,
  imports: [NewsarticleItemComponent, CommonModule],
  templateUrl: './newsarticle-list.component.html',
  styleUrl: './newsarticle-list.component.css'
})
export class NewsarticleListComponent implements OnInit, OnDestroy{
  myNewsArticles: NewsArticle[] | undefined;
  private subscription: Subscription | undefined;

  newsArticleService: NewsarticleService = inject(NewsarticleService);


  ngOnInit(): void {
    //newsarticles ophalen
    this.fetchArticles();

    // luisteren naar updates wanneer een newsarticle is aangemaakt
    this.newsArticleService.articleAdded$.subscribe(() => {
      this.fetchArticles();
    });
  }

  fetchArticles(): void {
    this.subscription = this.newsArticleService.getMyNewsArticles().subscribe({
      next: (articles) => {
        this.myNewsArticles = articles;
      },
      error: (err) => {
        console.error('Error met fetchen van news articles: ', err);
      }
    });
  }

  ngOnDestroy(): void {
      //opschonen van de subscription
      if (this.subscription) {
        this.subscription.unsubscribe();
      }
  }
  
  
}
