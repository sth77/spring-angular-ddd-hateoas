import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductionOrderListComponent } from './production-order-list.component';

describe('ProductionOrderListComponent', () => {
  let component: ProductionOrderListComponent;
  let fixture: ComponentFixture<ProductionOrderListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductionOrderListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductionOrderListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
