import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Notification } from '../../../shared/models/notification/notification.model';

@Component({
  selector: 'app-notification-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-item.component.html',
  styleUrl: './notification-item.component.css'
})
export class NotificationItemComponent {
  @Input() notification!: Notification;

}
