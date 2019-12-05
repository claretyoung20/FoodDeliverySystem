import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoodOrder } from 'app/shared/model/food-order.model';

@Component({
  selector: 'jhi-food-order-detail',
  templateUrl: './food-order-detail.component.html'
})
export class FoodOrderDetailComponent implements OnInit {
  foodOrder: IFoodOrder;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ foodOrder }) => {
      this.foodOrder = foodOrder;
    });
  }

  previousState() {
    window.history.back();
  }
}
