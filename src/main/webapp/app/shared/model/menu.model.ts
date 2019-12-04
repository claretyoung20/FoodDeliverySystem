import { Moment } from 'moment';

export interface IMenu {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  description?: string;
  name?: string;
  price?: number;
  imageContentType?: string;
  image?: any;
  isAvailable?: boolean;
  userLogin?: string;
  userId?: number;
}

export class Menu implements IMenu {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public description?: string,
    public name?: string,
    public price?: number,
    public imageContentType?: string,
    public image?: any,
    public isAvailable?: boolean,
    public userLogin?: string,
    public userId?: number
  ) {
    this.isAvailable = this.isAvailable || false;
  }
}
