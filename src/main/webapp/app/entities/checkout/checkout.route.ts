import { Injectable } from '@angular/core';

import { Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { CheckoutComponent } from 'app/entities/checkout/checkout/checkout.component';

@Injectable({ providedIn: 'root' })
export class CartResolve {
  constructor() {}
}

export const checkoutRoute: Routes = [
  {
    path: '',
    component: CheckoutComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'checkout'
    },
    canActivate: [UserRouteAccessService]
  }
];
