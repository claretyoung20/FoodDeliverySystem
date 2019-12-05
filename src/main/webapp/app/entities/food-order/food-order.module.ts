import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ByteworkSharedModule } from 'app/shared/shared.module';
import { FoodOrderComponent } from './food-order.component';
import { FoodOrderDetailComponent } from './food-order-detail.component';
import { FoodOrderUpdateComponent } from './food-order-update.component';
import { FoodOrderDeleteDialogComponent } from './food-order-delete-dialog.component';
import { foodOrderRoute } from './food-order.route';

@NgModule({
  imports: [ByteworkSharedModule, RouterModule.forChild(foodOrderRoute)],
  declarations: [FoodOrderComponent, FoodOrderDetailComponent, FoodOrderUpdateComponent, FoodOrderDeleteDialogComponent],
  entryComponents: [FoodOrderDeleteDialogComponent]
})
export class ByteworkFoodOrderModule {}
