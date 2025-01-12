import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { UserService } from '../user/user.service';
import { Observable } from 'rxjs';
import { Notification } from '../../notification/notification.model';
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  baseApiUrl: string = environment.apiUrl + '/notification/api/notification';
  //private baseApiUrl: string = 'http://localhost:8083/notification/api/notification';
  private httpClient: HttpClient = inject(HttpClient);
  private userService: UserService = inject(UserService);

  getMyNotifications(): Observable<Notification[]> {
    const username = this.userService.getUsername();
    const headers = new HttpHeaders({
      receiverUsernameWriter: username
    });

    return this.httpClient.get<Notification[]>(`${this.baseApiUrl}/mynotifications`, { headers });
  }
}
