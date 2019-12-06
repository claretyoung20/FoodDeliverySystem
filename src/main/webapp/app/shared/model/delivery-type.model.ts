import { Moment } from 'moment';

export interface IDeliveryType {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dType?: string;
}

export class DeliveryType implements IDeliveryType {
  constructor(public id?: number, public dateCreated?: Moment, public dateUpdated?: Moment, public dType?: string) {}
}
