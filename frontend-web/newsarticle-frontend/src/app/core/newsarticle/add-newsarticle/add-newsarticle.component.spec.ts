import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewsarticleComponent } from './add-newsarticle.component';

describe('AddNewsarticleComponent', () => {
  let component: AddNewsarticleComponent;
  let fixture: ComponentFixture<AddNewsarticleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddNewsarticleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNewsarticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
