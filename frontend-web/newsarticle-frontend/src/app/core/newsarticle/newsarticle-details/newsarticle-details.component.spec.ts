import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsarticleDetailsComponent } from './newsarticle-details.component';

describe('NewsarticleDetailsComponent', () => {
  let component: NewsarticleDetailsComponent;
  let fixture: ComponentFixture<NewsarticleDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsarticleDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsarticleDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
