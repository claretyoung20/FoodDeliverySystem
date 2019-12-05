import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from './food-order.service';

@Component({
  templateUrl: './food-order-delete-dialog.component.html'
})
export class FoodOrderDeleteDialogComponent {
  foodOrder: IFoodOrder;

  constructor(protected foodOrderService: FoodOrderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.foodOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'foodOrderListModification',
        content: 'Deleted an foodOrder'
      });
      this.activeModal.dismiss(true);
    });
  }
}
