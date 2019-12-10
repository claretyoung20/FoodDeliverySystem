import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'menu',
        loadChildren: () => import('./menu/menu.module').then(m => m.ByteworkMenuModule)
      },
      {
        path: 'cart',
        loadChildren: () => import('./cart/cart.module').then(m => m.ByteworkCartModule)
      },
      {
        path: 'checkout',
        loadChildren: () => import('./checkout/checkout.module').then(m => m.ByteworkCheckoutModule)
      },
      {
        path: 'food-order',
        loadChildren: () => import('./food-order/food-order.module').then(m => m.ByteworkFoodOrderModule)
      },
      {
        path: 'vendor-address',
        loadChildren: () => import('./vendor-address/vendor-address.module').then(m => m.ByteworkVendorAddressModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ByteworkEntityModule {}
