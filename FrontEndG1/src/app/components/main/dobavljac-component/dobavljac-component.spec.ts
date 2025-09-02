import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DobavljacComponent } from './dobavljac-component';

describe('DobavljacComponent', () => {
  let component: DobavljacComponent;
  let fixture: ComponentFixture<DobavljacComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DobavljacComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DobavljacComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
