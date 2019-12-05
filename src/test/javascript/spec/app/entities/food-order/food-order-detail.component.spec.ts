import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ByteworkTestModule } from '../../../test.module';
import { FoodOrderDetailComponent } from 'app/entities/food-order/food-order-detail.component';
import { FoodOrder } from 'app/shared/model/food-order.model';

describe('Component Tests', () => {
  describe('FoodOrder Management Detail Component', () => {
    let comp: FoodOrderDetailComponent;
    let fixture: ComponentFixture<FoodOrderDetailComponent>;
    const route = ({ data: of({ foodOrder: new FoodOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ByteworkTestModule],
        declarations: [FoodOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FoodOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoodOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.foodOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
