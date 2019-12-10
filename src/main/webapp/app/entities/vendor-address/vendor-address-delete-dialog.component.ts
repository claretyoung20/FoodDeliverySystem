import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendorAddress } from 'app/shared/model/vendor-address.model';
import { VendorAddressService } from './vendor-address.service';

@Component({
  templateUrl: './vendor-address-delete-dialog.component.html'
})
export class VendorAddressDeleteDialogComponent {
  vendorAddress: IVendorAddress;

  constructor(
    protected vendorAddressService: VendorAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vendorAddressService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'vendorAddressListModification',
        content: 'Deleted an vendorAddress'
      });
      this.activeModal.dismiss(true);
    });
  }
}
