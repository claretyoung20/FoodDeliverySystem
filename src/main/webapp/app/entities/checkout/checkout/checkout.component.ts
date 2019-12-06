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

@Component({
  selector: 'jhi-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  carts: ICart[];
  account: Account;
  baseTotal = 0;
  discountPrice = 0;
  finalTotal = 0;
  finalT;
  totalCartItem = 0;
  deliveryT = '';
  paymentType = 'ON_DELIVERY';
  deliverytypes: IDeliveryType[];
  paymentmethods: IPaymentMethod[];

  constructor(
    protected cartService: CartService,
    private accountService: AccountService,
    protected deliveryTypeService: DeliveryTypeService,
    protected paymentMethodService: PaymentMethodService
  ) {}

  ngOnInit() {
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });

    this.loadAll();
    this.loadDeliveryType();
    this.loadPaymentMethod();
  }

  loadAll() {
    this.cartService.queryById(this.account.id, {}).subscribe((res: HttpResponse<ICart[]>) => this.paginateCarts(res.body));
  }

  loadDeliveryType() {
    this.deliveryTypeService.query().subscribe((res: HttpResponse<IDeliveryType[]>) => (this.deliverytypes = res.body));
  }

  loadPaymentMethod() {
    this.paymentMethodService.query().subscribe((res: HttpResponse<IPaymentMethod[]>) => (this.paymentmethods = res.body));
  }

  protected paginateCarts(data: ICart[]) {
    this.carts = data;
    this.totalCartItem = this.carts.length;
    this.baseTotal = this.carts.reduce((a, b) => a + b.menuPrice, 0);
  }

  checkDT(type) {
    this.deliveryT = type;
  }

  checkPM(method) {
    this.paymentType = method;

    if (method === PAYMENT_METHOD_CARD) {
      this.discountPrice = (2.5 / 100) * this.baseTotal;
    } else {
      this.discountPrice = 0;
    }
  }
}
