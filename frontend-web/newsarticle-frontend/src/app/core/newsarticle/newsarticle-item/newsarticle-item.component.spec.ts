import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NewsarticleItemComponent } from './newsarticle-item.component';
import { NewsArticle } from '../../../shared/models/newsarticle/newsarticle.model';
import { Router } from '@angular/router';
import { NewsarticleService } from '../../../shared/models/services/newsarticle/newsarticle.service';
import { UserService } from '../../../shared/models/services/user/user.service';
import { ReviewService } from '../../../shared/models/services/review/review.service';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { By } from '@angular/platform-browser';

describe('NewsarticleItemComponent', () => {
  let component: NewsarticleItemComponent;
  let fixture: ComponentFixture<NewsarticleItemComponent>;
  let routerSpy: jasmine.SpyObj<Router>;
  let userServiceSpy: jasmine.SpyObj<UserService>;
  let newsArticleServiceSpy: jasmine.SpyObj<NewsarticleService>;
  let reviewServiceSpy: jasmine.SpyObj<ReviewService>;

  const mockNewsArticle: NewsArticle = {
    id: 1,
    title: 'Test Article',
    content: 'This is a test article',
    usernameWriter: 'testuser',
    creationDate: new Date(),
    status: 'CONCEPT'
  };

  beforeEach(async () => {
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    userServiceSpy = jasmine.createSpyObj('UserService', ['getRole', 'getUsername']);
    newsArticleServiceSpy = jasmine.createSpyObj('NewsarticleService', ['submitForApproval']);
    reviewServiceSpy = jasmine.createSpyObj('ReviewService', ['approveNewsArticle', 'disapproveNewsArticle']);

    await TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [NewsarticleItemComponent],
      providers: [
        { provide: Router, useValue: routerSpy },
        { provide: UserService, useValue: userServiceSpy },
        { provide: NewsarticleService, useValue: newsArticleServiceSpy },
        { provide: ReviewService, useValue: reviewServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(NewsarticleItemComponent);
    component = fixture.componentInstance;
    component.newsArticle = mockNewsArticle;

    userServiceSpy.getRole.and.returnValue('writer');
    userServiceSpy.getUsername.and.returnValue('testuser');

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should display article details', () => {
    const titleElement = fixture.debugElement.query(By.css('h2')).nativeElement;
    const contentElement = fixture.debugElement.query(By.css('p.text-gray-600')).nativeElement;
    expect(titleElement.textContent).toContain(mockNewsArticle.title);
    expect(contentElement.textContent).toContain(mockNewsArticle.content);
  });

  it('should navigate to details page when "View Details" button is clicked', () => {
    const button = fixture.debugElement.query(By.css('button')).nativeElement;
    button.click();
    expect(routerSpy.navigate).toHaveBeenCalledWith([`/article/${mockNewsArticle.id}`]);
  });

  it('should call editArticle when "Edit" button is clicked', () => {
    spyOn(component, 'editArticle');
    const editButton = fixture.debugElement.query(By.css('button')).nativeElement;
    editButton.click();
    expect(component.editArticle).toHaveBeenCalledWith(mockNewsArticle.id!);
  });

  it('should call submitForApproval when "Submit for Approval" button is clicked', () => {
    component.submitForApproval(mockNewsArticle.id!);
    expect(newsArticleServiceSpy.submitForApproval).toHaveBeenCalledWith(mockNewsArticle.id!);
    expect(component.newsArticle.status).toBe('SUBMITTED');
  });

  it('should call confirmApprove and update status when approve is confirmed', () => {
    component.remark = 'Approved';
    component.confirmApprove(mockNewsArticle.id!);
    expect(reviewServiceSpy.approveNewsArticle).toHaveBeenCalledWith(mockNewsArticle.id!, 'Approved');
    expect(component.newsArticle.status).toBe('APPROVED');
  });

  it('should call disapproveNewsArticle and update status when disapprove is confirmed', () => {
    component.remark = 'Not suitable';
    component.disapproveNewsArticle(mockNewsArticle.id!);
    expect(reviewServiceSpy.disapproveNewsArticle).toHaveBeenCalledWith(mockNewsArticle.id!, 'Not suitable');
    expect(component.newsArticle.status).toBe('DISAPPROVED');
  });

  it('should disable "Submit for Approval" button if status is not CONCEPT', () => {
    component.newsArticle.status = 'APPROVED';
    fixture.detectChanges();
    const button = fixture.debugElement.query(By.css('button[disabled]'));
    expect(button).toBeTruthy();
  });

  it('should only show "Approve Article" button for editors with SUBMITTED articles', () => {
    userServiceSpy.getRole.and.returnValue('editor');
    component.newsArticle.status = 'SUBMITTED';
    fixture.detectChanges();
    const approveButton = fixture.debugElement.query(By.css('button.bg-green-500'));
    expect(approveButton).toBeTruthy();
  });

  it('should disable "Approve Article" button if writer and editor are the same user', () => {
    userServiceSpy.getUsername.and.returnValue(mockNewsArticle.usernameWriter);
    component.newsArticle.status = 'SUBMITTED';
    fixture.detectChanges();
    const approveButton = fixture.debugElement.query(By.css('button.bg-gray-400[disabled]'));
    expect(approveButton).toBeTruthy();
  });
});
