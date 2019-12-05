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
import { IFoodOrder, FoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from './food-order.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IOrderStatusModel } from 'app/shared/model/order-status.model';
import { IPaymentMethodModel } from 'app/shared/model/payment-method.model';
import { OrderStatusService } from 'app/shared/services/order-status/order-status.service';
import { PaymentMethodService } from 'app/shared/services/payment-method/payment-method.service';

@Component({
  selector: 'jhi-food-order-update',
  templateUrl: './food-order-update.component.html'
})
export class FoodOrderUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  orderstatuses: IOrderStatusModel[];

  paymentmethods: IPaymentMethodModel[];

  editForm = this.fb.group({
    id: [],
    baseTotal: [null, [Validators.required]],
    finalTotal: [null, [Validators.required, Validators.min(1)]],
    vendorId: [null, [Validators.required]],
    dateCreated: [],
    dateUpdated: [],
    userId: [null, Validators.required],
    orderStatusId: [],
    paymentMethodId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected foodOrderService: FoodOrderService,
    protected userService: UserService,
    protected orderStatusService: OrderStatusService,
    protected paymentMethodService: PaymentMethodService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ foodOrder }) => {
      this.updateForm(foodOrder);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.orderStatusService
      .query()
      .subscribe(
        (res: HttpResponse<IOrderStatusModel[]>) => (this.orderstatuses = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.paymentMethodService
      .query()
      .subscribe(
        (res: HttpResponse<IPaymentMethodModel[]>) => (this.paymentmethods = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(foodOrder: IFoodOrder) {
    this.editForm.patchValue({
      id: foodOrder.id,
      baseTotal: foodOrder.baseTotal,
      finalTotal: foodOrder.finalTotal,
      vendorId: foodOrder.vendorId,
      dateCreated: foodOrder.dateCreated != null ? foodOrder.dateCreated.format(DATE_TIME_FORMAT) : null,
      dateUpdated: foodOrder.dateUpdated != null ? foodOrder.dateUpdated.format(DATE_TIME_FORMAT) : null,
      userId: foodOrder.userId,
      orderStatusId: foodOrder.orderStatusId,
      paymentMethodId: foodOrder.paymentMethodId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const foodOrder = this.createFromForm();
    if (foodOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.foodOrderService.update(foodOrder));
    } else {
      this.subscribeToSaveResponse(this.foodOrderService.create(foodOrder));
    }
  }

  private createFromForm(): IFoodOrder {
    return {
      ...new FoodOrder(),
      id: this.editForm.get(['id']).value,
      baseTotal: this.editForm.get(['baseTotal']).value,
      finalTotal: this.editForm.get(['finalTotal']).value,
      vendorId: this.editForm.get(['vendorId']).value,
      dateCreated:
        this.editForm.get(['dateCreated']).value != null ? moment(this.editForm.get(['dateCreated']).value, DATE_TIME_FORMAT) : undefined,
      dateUpdated:
        this.editForm.get(['dateUpdated']).value != null ? moment(this.editForm.get(['dateUpdated']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value,
      orderStatusId: this.editForm.get(['orderStatusId']).value,
      paymentMethodId: this.editForm.get(['paymentMethodId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodOrder>>) {
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

  trackOrderStatusById(index: number, item: IOrderStatusModel) {
    return item.id;
  }

  trackPaymentMethodById(index: number, item: IPaymentMethodModel) {
    return item.id;
  }
}
