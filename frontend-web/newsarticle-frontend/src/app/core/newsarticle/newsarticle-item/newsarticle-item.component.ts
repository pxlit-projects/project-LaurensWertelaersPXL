import { Component, Input } from '@angular/core';
import { NewsArticle } from '../../../shared/models/newsarticle/newsarticle.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-newsarticle-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './newsarticle-item.component.html',
  styleUrl: './newsarticle-item.component.css'
})
export class NewsarticleItemComponent {
  @Input() newsArticle!: NewsArticle;

}
