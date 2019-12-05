import { Moment } from 'moment';

export interface ICart {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  menuName?: string;
  menuId?: number;
  userLogin?: string;
  userId?: number;
  menuPrice?: number;
}

export class Cart implements ICart {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public menuName?: string,
    public menuId?: number,
    public userLogin?: string,
    public userId?: number,
    public menuPrice?: number
  ) {}
}
