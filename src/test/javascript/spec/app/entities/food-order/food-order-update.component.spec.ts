import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ByteworkTestModule } from '../../../test.module';
import { FoodOrderUpdateComponent } from 'app/entities/food-order/food-order-update.component';
import { FoodOrderService } from 'app/entities/food-order/food-order.service';
import { FoodOrder } from 'app/shared/model/food-order.model';

describe('Component Tests', () => {
  describe('FoodOrder Management Update Component', () => {
    let comp: FoodOrderUpdateComponent;
    let fixture: ComponentFixture<FoodOrderUpdateComponent>;
    let service: FoodOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ByteworkTestModule],
        declarations: [FoodOrderUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FoodOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoodOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FoodOrder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FoodOrder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
