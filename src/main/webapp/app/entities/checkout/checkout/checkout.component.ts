import { Component, OnInit } from '@angular/core';
import { ICart } from 'app/shared/model/cart.model';
import { Account } from 'app/core/user/account.model';
import { CartService } from 'app/entities/cart/cart.service';
import { AccountService } from 'app/core/auth/account.service';
import { HttpResponse } from '@angular/common/http';
import { DeliveryTypeService } from 'app/shared/services/delivery-type/delivery-type.service';
import { IDeliveryType } from 'app/shared/model/delivery-type.model';
import { IPaymentMethod } from 'app/shared/model/payment-method.model';
import { PaymentMethodService } from 'app/shared/services/payment-method/payment-method.service';
import { PAYMENT_METHOD_CARD } from 'app/app.constants';
import { VendorAddressService } from 'app/entities/vendor-address/vendor-address.service';
import { IFoodOrder } from 'app/shared/model/food-order.model';
import { FoodOrderService } from 'app/entities/food-order/food-order.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  carts: ICart[];
  account: Account;
  baseTotal = 0;
  shippingFee = 0;
  discountPrice = 0;
  finalTotal = 0;
  totalCartItem = 0;
  deliveryT = '';
  paymentType = 'ON_DELIVERY';
  paymentTypeId = 0;
  deliverytypeId = 0;
  deliverytypes: IDeliveryType[];
  paymentmethods: IPaymentMethod[];

  constructor(
    protected cartService: CartService,
    private accountService: AccountService,
    protected deliveryTypeService: DeliveryTypeService,
    protected paymentMethodService: PaymentMethodService,
    protected vendorAddressService: VendorAddressService,
    protected foodOrderService: FoodOrderService,
    private router: Router,
    protected jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });

    this.loadAll();
    this.loadDeliveryType();
    this.loadPaymentMethod();
    this.getShippingPrice();
  }

  loadAll() {
    this.cartService.queryById(this.account.id, {}).subscribe((res: HttpResponse<ICart[]>) => this.paginateCarts(res.body));
  }

  loadDeliveryType() {
    this.deliveryTypeService.query().subscribe((res: HttpResponse<IDeliveryType[]>) => this.dTypeload(res.body));
  }

  dTypeload(data) {
    this.deliverytypes = data;
    this.deliverytypeId = this.deliverytypes[this.deliverytypes.length - 1].id;
  }

  dPaymentload(data) {
    this.paymentmethods = data;
    this.paymentTypeId = this.paymentmethods[this.paymentmethods.length - 1].id;
  }

  loadPaymentMethod() {
    this.paymentMethodService.query().subscribe((res: HttpResponse<IPaymentMethod[]>) => this.dPaymentload(res.body));
  }

  protected paginateCarts(data: ICart[]) {
    this.carts = data;
    this.totalCartItem = this.carts.length;
    this.baseTotal = this.carts.reduce((a, b) => a + b.menuPrice, 0);
  }

  checkDT(type, id) {
    this.deliveryT = type;
    this.deliverytypeId = id;
  }

  checkPM(method, id) {
    this.paymentType = method;
    this.paymentTypeId = id;
    if (method === PAYMENT_METHOD_CARD) {
      this.discountPrice = (2.5 / 100) * (this.baseTotal + this.shippingFee);
    } else {
      this.discountPrice = 0;
    }
  }

  getShippingPrice() {
    // system support shipping to one vendor
    const vendorId = 5;
    // get shipping fee
    this.vendorAddressService.queryByUserId(vendorId).subscribe((res: any) => {
      this.calculateShippingFee(res.body);
    });
  }

  calculateShippingFee(shippingFee) {
    // #10/meter
    this.shippingFee = shippingFee * 10;
  }

  checkout() {
    const foodOrder: IFoodOrder = {};
    foodOrder.vendorId = 5;
    foodOrder.userId = this.account.id;
    foodOrder.paymentMethodId = this.paymentTypeId;
    foodOrder.deliveryTypeId = this.deliverytypeId;
    foodOrder.baseTotal = 0;
    foodOrder.finalTotal = 1;
    this.subscribeToSaveResponse(this.foodOrderService.create(foodOrder));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodOrder>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.jhiAlertService.success('Order successfully placed', null, null);
    this.router.navigate(['/food-order']);
  }

  protected onSaveError() {
    alert('Not successful');
  }
}
