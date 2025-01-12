import { Component, inject } from '@angular/core';
import { NewsarticleListComponent } from "../newsarticle/newsarticle-list/newsarticle-list.component";
import { AddNewsarticleComponent } from "../newsarticle/add-newsarticle/add-newsarticle.component";
import { NotificationListComponent } from "../notification/notification-list/notification-list.component";

@Component({
  selector: 'app-writer',
  standalone: true,
  imports: [NewsarticleListComponent, AddNewsarticleComponent, NotificationListComponent],
  templateUrl: './writer.component.html',
  styleUrl: './writer.component.css'
})
export class WriterComponent {

}
