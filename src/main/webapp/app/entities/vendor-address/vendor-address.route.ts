import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { VendorAddress } from 'app/shared/model/vendor-address.model';
import { VendorAddressService } from './vendor-address.service';
import { VendorAddressComponent } from './vendor-address.component';
import { VendorAddressDetailComponent } from './vendor-address-detail.component';
import { VendorAddressUpdateComponent } from './vendor-address-update.component';
import { IVendorAddress } from 'app/shared/model/vendor-address.model';

@Injectable({ providedIn: 'root' })
export class VendorAddressResolve implements Resolve<IVendorAddress> {
  constructor(private service: VendorAddressService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVendorAddress> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((vendorAddress: HttpResponse<VendorAddress>) => vendorAddress.body));
    }
    return of(new VendorAddress());
  }
}

export const vendorAddressRoute: Routes = [
  {
    path: '',
    component: VendorAddressComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_VENDOR'],
      defaultSort: 'id,asc',
      pageTitle: 'VendorAddresses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VendorAddressDetailComponent,
    resolve: {
      vendorAddress: VendorAddressResolve
    },
    data: {
      authorities: ['ROLE_VENDOR'],
      pageTitle: 'VendorAddresses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VendorAddressUpdateComponent,
    resolve: {
      vendorAddress: VendorAddressResolve
    },
    data: {
      authorities: ['ROLE_VENDOR'],
      pageTitle: 'VendorAddresses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VendorAddressUpdateComponent,
    resolve: {
      vendorAddress: VendorAddressResolve
    },
    data: {
      authorities: ['ROLE_VENDOR'],
      pageTitle: 'VendorAddresses'
    },
    canActivate: [UserRouteAccessService]
  }
];
