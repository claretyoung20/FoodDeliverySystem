import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeliveryType } from 'app/shared/model/delivery-type.model';

type EntityResponseType = HttpResponse<IDeliveryType>;
type EntityArrayResponseType = HttpResponse<IDeliveryType[]>;

@Injectable({ providedIn: 'root' })
@Injectable({
  providedIn: 'root'
})
export class DeliveryTypeService {
  public resourceUrl = SERVER_API_URL + 'api/delivery-types';

  constructor(protected http: HttpClient) {}

  create(deliveryType: IDeliveryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryType);
    return this.http
      .post<IDeliveryType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deliveryType: IDeliveryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryType);
    return this.http
      .put<IDeliveryType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeliveryType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeliveryType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(deliveryType: IDeliveryType): IDeliveryType {
    const copy: IDeliveryType = Object.assign({}, deliveryType, {
      dateCreated: deliveryType.dateCreated != null && deliveryType.dateCreated.isValid() ? deliveryType.dateCreated.toJSON() : null,
      dateUpdated: deliveryType.dateUpdated != null && deliveryType.dateUpdated.isValid() ? deliveryType.dateUpdated.toJSON() : null
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
      res.body.forEach((deliveryType: IDeliveryType) => {
        deliveryType.dateCreated = deliveryType.dateCreated != null ? moment(deliveryType.dateCreated) : null;
        deliveryType.dateUpdated = deliveryType.dateUpdated != null ? moment(deliveryType.dateUpdated) : null;
      });
    }
    return res;
  }
}
