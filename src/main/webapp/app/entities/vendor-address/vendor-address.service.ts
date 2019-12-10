import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVendorAddress } from 'app/shared/model/vendor-address.model';

type EntityResponseType = HttpResponse<IVendorAddress>;
type EntityArrayResponseType = HttpResponse<IVendorAddress[]>;

@Injectable({ providedIn: 'root' })
export class VendorAddressService {
  public resourceUrl = SERVER_API_URL + 'api/vendor-addresses';

  constructor(protected http: HttpClient) {}

  create(vendorAddress: IVendorAddress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vendorAddress);
    return this.http
      .post<IVendorAddress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vendorAddress: IVendorAddress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vendorAddress);
    return this.http
      .put<IVendorAddress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVendorAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVendorAddress[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryById(id, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    const url = `${this.resourceUrl}/vendor/${id}`;
    return this.http
      .get<IVendorAddress[]>(url, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByUserId(id, req?: any): Observable<any> {
    const options = createRequestOption(req);
    const url = `${this.resourceUrl}/shippingFee/${id}`;
    return this.http.get<any>(url, { params: options, observe: 'response' }).pipe();
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vendorAddress: IVendorAddress): IVendorAddress {
    const copy: IVendorAddress = Object.assign({}, vendorAddress, {
      dateCreated: vendorAddress.dateCreated != null && vendorAddress.dateCreated.isValid() ? vendorAddress.dateCreated.toJSON() : null,
      dateUpdated: vendorAddress.dateUpdated != null && vendorAddress.dateUpdated.isValid() ? vendorAddress.dateUpdated.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
      res.body.dateUpdated = res.body.dateUpdated != null ? moment(res.body.dateUpdated) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vendorAddress: IVendorAddress) => {
        vendorAddress.dateCreated = vendorAddress.dateCreated != null ? moment(vendorAddress.dateCreated) : null;
        vendorAddress.dateUpdated = vendorAddress.dateUpdated != null ? moment(vendorAddress.dateUpdated) : null;
      });
    }
    return res;
  }
}
