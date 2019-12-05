import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ByteworkSharedModule } from 'app/shared/shared.module';
import { checkoutRoute } from './checkout.route';
import { CheckoutComponent } from './checkout/checkout.component';

@NgModule({
  imports: [ByteworkSharedModule, RouterModule.forChild(checkoutRoute)],
  declarations: [CheckoutComponent],
  entryComponents: []
})
export class ByteworkCheckoutModule {}
