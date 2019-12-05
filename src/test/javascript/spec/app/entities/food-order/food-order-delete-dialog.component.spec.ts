import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ByteworkTestModule } from '../../../test.module';
import { FoodOrderDeleteDialogComponent } from 'app/entities/food-order/food-order-delete-dialog.component';
import { FoodOrderService } from 'app/entities/food-order/food-order.service';

describe('Component Tests', () => {
  describe('FoodOrder Management Delete Component', () => {
    let comp: FoodOrderDeleteDialogComponent;
    let fixture: ComponentFixture<FoodOrderDeleteDialogComponent>;
    let service: FoodOrderService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ByteworkTestModule],
        declarations: [FoodOrderDeleteDialogComponent]
      })
        .overrideTemplate(FoodOrderDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FoodOrderDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodOrderService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
