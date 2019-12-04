import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Menu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';
import { MenuComponent } from './menu.component';
import { MenuDetailComponent } from './menu-detail.component';
import { MenuUpdateComponent } from './menu-update.component';
import { IMenu } from 'app/shared/model/menu.model';

@Injectable({ providedIn: 'root' })
export class MenuResolve implements Resolve<IMenu> {
  constructor(private service: MenuService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMenu> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((menu: HttpResponse<Menu>) => menu.body));
    }
    return of(new Menu());
  }
}

export const menuRoute: Routes = [
  {
    path: '',
    component: MenuComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_VENDOR', 'ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MenuDetailComponent,
    resolve: {
      menu: MenuResolve
    },
    data: {
      authorities: ['ROLE_VENDOR', 'ROLE_USER'],
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MenuUpdateComponent,
    resolve: {
      menu: MenuResolve
    },
    data: {
      authorities: ['ROLE_VENDOR'],
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MenuUpdateComponent,
    resolve: {
      menu: MenuResolve
    },
    data: {
      authorities: ['ROLE_VENDOR'],
      pageTitle: 'Menus'
    },
    canActivate: [UserRouteAccessService]
  }
];
