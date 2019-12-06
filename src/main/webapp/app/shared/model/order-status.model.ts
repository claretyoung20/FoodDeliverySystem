import { Moment } from 'moment';

export interface IOrderStatus {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  status?: string;
}

export class OrderStatus implements IOrderStatus {
  constructor(public id?: number, public dateCreated?: Moment, public dateUpdated?: Moment, public status?: string) {}
}
