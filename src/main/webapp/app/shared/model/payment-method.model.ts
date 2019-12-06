import { Moment } from 'moment';

export interface IPaymentMethod {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  method?: number;
}

export class PaymentMethod implements IPaymentMethod {
  constructor(public id?: number, public dateCreated?: Moment, public dateUpdated?: Moment, public method?: number) {}
}
