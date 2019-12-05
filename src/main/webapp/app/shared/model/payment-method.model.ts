import { Moment } from 'moment';

export interface IPaymentMethodModel {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  method?: number;
}

export class PaymentMethod implements IPaymentMethodModel {
  constructor(public id?: number, public dateCreated?: Moment, public dateUpdated?: Moment, public method?: number) {}
}
