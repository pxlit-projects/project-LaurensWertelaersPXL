import { Component } from '@angular/core';
import { NewsarticleListComponent } from '../newsarticle/newsarticle-list/newsarticle-list.component';

@Component({
  selector: 'app-member',
  standalone: true,
  imports: [NewsarticleListComponent],
  templateUrl: './member.component.html',
  styleUrl: './member.component.css'
})
export class MemberComponent {

}
