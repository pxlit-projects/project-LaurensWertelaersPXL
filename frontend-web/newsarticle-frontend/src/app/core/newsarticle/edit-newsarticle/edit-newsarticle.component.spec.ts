import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditNewsarticleComponent } from './edit-newsarticle.component';

describe('EditNewsarticleComponent', () => {
  let component: EditNewsarticleComponent;
  let fixture: ComponentFixture<EditNewsarticleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditNewsarticleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditNewsarticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
