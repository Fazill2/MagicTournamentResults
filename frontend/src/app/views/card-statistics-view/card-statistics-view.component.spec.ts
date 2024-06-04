import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardStatisticsViewComponent } from './card-statistics-view.component';

describe('CardStatisticsViewComponent', () => {
  let component: CardStatisticsViewComponent;
  let fixture: ComponentFixture<CardStatisticsViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardStatisticsViewComponent]
    });
    fixture = TestBed.createComponent(CardStatisticsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
