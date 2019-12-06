import { Moment } from 'moment';

export interface IFoodOrder {
  id?: number;
  baseTotal?: number;
  finalTotal?: number;
  vendorId?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userLogin?: string;
  userId?: number;
  orderStatusStatus?: string;
  orderStatusId?: number;
  paymentMethodMethod?: string;
  paymentMethodId?: number;
  deliveryTypeDType?: string;
  deliveryTypeId?: number;
}

export class FoodOrder implements IFoodOrder {
  constructor(
    public id?: number,
    public baseTotal?: number,
    public finalTotal?: number,
    public vendorId?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userLogin?: string,
    public userId?: number,
    public orderStatusStatus?: string,
    public orderStatusId?: number,
    public paymentMethodMethod?: string,
    public paymentMethodId?: number,
    public deliveryTypeDType?: string,
    public deliveryTypeId?: number
  ) {}
}
