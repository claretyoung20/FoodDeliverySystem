import { Moment } from 'moment';

export interface IOrderStatusModel {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  status?: string;
}

export class Menu implements IOrderStatusModel {
  constructor(public id?: number, public dateCreated?: Moment, public dateUpdated?: Moment, public status?: string) {}
}
