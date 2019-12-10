import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ByteworkSharedModule } from 'app/shared/shared.module';
import { VendorAddressComponent } from './vendor-address.component';
import { VendorAddressDetailComponent } from './vendor-address-detail.component';
import { VendorAddressUpdateComponent } from './vendor-address-update.component';
import { VendorAddressDeleteDialogComponent } from './vendor-address-delete-dialog.component';
import { vendorAddressRoute } from './vendor-address.route';

@NgModule({
  imports: [ByteworkSharedModule, RouterModule.forChild(vendorAddressRoute)],
  declarations: [VendorAddressComponent, VendorAddressDetailComponent, VendorAddressUpdateComponent, VendorAddressDeleteDialogComponent],
  entryComponents: [VendorAddressDeleteDialogComponent]
})
export class ByteworkVendorAddressModule {}
