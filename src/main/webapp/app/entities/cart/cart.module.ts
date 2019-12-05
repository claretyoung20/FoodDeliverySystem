import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ByteworkSharedModule } from 'app/shared/shared.module';
import { CartComponent } from './cart.component';
import { CartDetailComponent } from './cart-detail.component';
import { CartUpdateComponent } from './cart-update.component';
import { CartDeleteDialogComponent } from './cart-delete-dialog.component';
import { cartRoute } from './cart.route';

@NgModule({
  imports: [ByteworkSharedModule, RouterModule.forChild(cartRoute)],
  declarations: [CartComponent, CartDetailComponent, CartUpdateComponent, CartDeleteDialogComponent],
  entryComponents: [CartDeleteDialogComponent]
})
export class ByteworkCartModule {}
