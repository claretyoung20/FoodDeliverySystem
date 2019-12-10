import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IVendorAddress, VendorAddress } from 'app/shared/model/vendor-address.model';
import { VendorAddressService } from './vendor-address.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-vendor-address-update',
  templateUrl: './vendor-address-update.component.html'
})
export class VendorAddressUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    fullAdress: [null, [Validators.required]],
    vLat: [null, [Validators.required]],
    vLng: [null, [Validators.required]],
    dateCreated: [],
    dateUpdated: [],
    userId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vendorAddressService: VendorAddressService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vendorAddress }) => {
      this.updateForm(vendorAddress);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vendorAddress: IVendorAddress) {
    this.editForm.patchValue({
      id: vendorAddress.id,
      fullAdress: vendorAddress.fullAdress,
      vLat: vendorAddress.vLat,
      vLng: vendorAddress.vLng,
      dateCreated: vendorAddress.dateCreated != null ? vendorAddress.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: vendorAddress.dateUpdated != null ? vendorAddress.dateUpdated.format(DATE_TIME_FORMAT) : null,
      userId: vendorAddress.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vendorAddress = this.createFromForm();
    if (vendorAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.vendorAddressService.update(vendorAddress));
    } else {
      this.subscribeToSaveResponse(this.vendorAddressService.create(vendorAddress));
    }
  }

  private createFromForm(): IVendorAddress {
    return {
      ...new VendorAddress(),
      id: this.editForm.get(['id']).value,
      fullAdress: this.editForm.get(['fullAdress']).value,
      vLat: this.editForm.get(['vLat']).value,
      vLng: this.editForm.get(['vLng']).value,
      dateCreated:
        this.editForm.get(['dateCreated']).value != null ? moment(this.editForm.get(['dateCreated']).value, DATE_TIME_FORMAT) : undefined,
      dateUpdated:
        this.editForm.get(['dateUpdated']).value != null ? moment(this.editForm.get(['dateUpdated']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVendorAddress>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
