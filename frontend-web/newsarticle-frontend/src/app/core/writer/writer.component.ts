import { Component, inject } from '@angular/core';
import { UserService } from '../../shared/models/services/user/user.service';
import { NewsarticleListComponent } from "../newsarticle/newsarticle-list/newsarticle-list.component";
import { AddNewsarticleComponent } from "../newsarticle/add-newsarticle/add-newsarticle.component";

@Component({
  selector: 'app-writer',
  standalone: true,
  imports: [NewsarticleListComponent, AddNewsarticleComponent],
  templateUrl: './writer.component.html',
  styleUrl: './writer.component.css'
})
export class WriterComponent {

}
