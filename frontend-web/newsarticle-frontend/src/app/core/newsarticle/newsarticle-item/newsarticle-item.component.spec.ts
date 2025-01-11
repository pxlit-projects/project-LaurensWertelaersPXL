import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsarticleItemComponent } from './newsarticle-item.component';

describe('NewsarticleItemComponent', () => {
  let component: NewsarticleItemComponent;
  let fixture: ComponentFixture<NewsarticleItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewsarticleItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsarticleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
