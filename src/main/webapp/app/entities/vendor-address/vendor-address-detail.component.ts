import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendorAddress } from 'app/shared/model/vendor-address.model';

@Component({
  selector: 'jhi-vendor-address-detail',
  templateUrl: './vendor-address-detail.component.html'
})
export class VendorAddressDetailComponent implements OnInit {
  vendorAddress: IVendorAddress;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vendorAddress }) => {
      this.vendorAddress = vendorAddress;
    });
  }

  previousState() {
    window.history.back();
  }
}
