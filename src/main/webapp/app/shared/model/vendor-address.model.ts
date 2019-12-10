import { Moment } from 'moment';

export interface IVendorAddress {
  id?: number;
  fullAdress?: string;
  vLat?: number;
  vLng?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userLogin?: string;
  userId?: number;
}

export class VendorAddress implements IVendorAddress {
  constructor(
    public id?: number,
    public fullAdress?: string,
    public vLat?: number,
    public vLng?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userLogin?: string,
    public userId?: number
  ) {}
}
