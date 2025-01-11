import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsarticleListComponent } from './newsarticle-list.component';

describe('NewsarticleListComponent', () => {
  let component: NewsarticleListComponent;
  let fixture: ComponentFixture<NewsarticleListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsarticleListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsarticleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
