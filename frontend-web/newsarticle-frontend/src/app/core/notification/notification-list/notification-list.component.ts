import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { NotificationItemComponent } from '../notification-item/notification-item.component';
import { CommonModule } from '@angular/common';
import { Notification } from '../../../shared/models/notification/notification.model';
import { Subscription } from 'rxjs';
import { NotificationService } from '../../../shared/models/services/notification/notification.service';

@Component({
  selector: 'app-notification-list',
  standalone: true,
  imports: [NotificationItemComponent, CommonModule],
  templateUrl: './notification-list.component.html',
  styleUrl: './notification-list.component.css'
})
export class NotificationListComponent implements OnInit, OnDestroy {
  myNotifications: Notification[] | undefined;
  private subscription: Subscription | undefined;
  private notificationService: NotificationService = inject(NotificationService);

  ngOnInit(): void {
    this.fetchNotifications();
  }

  fetchNotifications(): void {
    this.subscription = this.notificationService.getMyNotifications().subscribe({
      next: (notifications) => {
        this.myNotifications = notifications;
      },
      error: (err) => {
        console.error('Error fetching notifications: ', err);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
