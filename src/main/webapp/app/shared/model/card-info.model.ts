import { Moment } from 'moment';

export interface ICardInfoModel {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  nameOnCard?: string;
  expireDate?: string;
  cvv?: string;
  cardNumber?: string;
}

export class Menu implements ICardInfoModel {
  constructor(
    public id?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public nameOnCard?: string,
    public expireDate?: string,
    public cvv?: string,
    public cardNumber?: string
  ) {}
}
