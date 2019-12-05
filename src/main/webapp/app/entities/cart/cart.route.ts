import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Cart } from 'app/shared/model/cart.model';
import { CartService } from './cart.service';
import { CartComponent } from './cart.component';
import { CartDetailComponent } from './cart-detail.component';
import { CartUpdateComponent } from './cart-update.component';
import { ICart } from 'app/shared/model/cart.model';

@Injectable({ providedIn: 'root' })
export class CartResolve implements Resolve<ICart> {
  constructor(private service: CartService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICart> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((cart: HttpResponse<Cart>) => cart.body));
    }
    return of(new Cart());
  }
}

export const cartRoute: Routes = [
  {
    path: '',
    component: CartComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'Carts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CartDetailComponent,
    resolve: {
      cart: CartResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN'],
      pageTitle: 'Carts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CartUpdateComponent,
    resolve: {
      cart: CartResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'Carts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CartUpdateComponent,
    resolve: {
      cart: CartResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN'],
      pageTitle: 'Carts'
    },
    canActivate: [UserRouteAccessService]
  }
];
