import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StavkaPorudzbineComponent } from './stavka-porudzbine-component';

describe('StavkaPorudzbineComponent', () => {
  let component: StavkaPorudzbineComponent;
  let fixture: ComponentFixture<StavkaPorudzbineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StavkaPorudzbineComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StavkaPorudzbineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
