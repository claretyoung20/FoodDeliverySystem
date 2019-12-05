import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from './food-order.service';
import { FoodOrderComponent } from './food-order.component';
import { FoodOrderDetailComponent } from './food-order-detail.component';
import { FoodOrderUpdateComponent } from './food-order-update.component';
import { IFoodOrder } from 'app/shared/model/food-order.model';

@Injectable({ providedIn: 'root' })
export class FoodOrderResolve implements Resolve<IFoodOrder> {
  constructor(private service: FoodOrderService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFoodOrder> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((foodOrder: HttpResponse<FoodOrder>) => foodOrder.body));
    }
    return of(new FoodOrder());
  }
}

export const foodOrderRoute: Routes = [
  {
    path: '',
    component: FoodOrderComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'FoodOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FoodOrderDetailComponent,
    resolve: {
      foodOrder: FoodOrderResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'FoodOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FoodOrderUpdateComponent,
    resolve: {
      foodOrder: FoodOrderResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'FoodOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FoodOrderUpdateComponent,
    resolve: {
      foodOrder: FoodOrderResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'FoodOrders'
    },
    canActivate: [UserRouteAccessService]
  }
];
