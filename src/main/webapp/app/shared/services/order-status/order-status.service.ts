import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderStatusModel } from 'app/shared/model/order-status.model';

type EntityResponseType = HttpResponse<IOrderStatusModel>;
type EntityArrayResponseType = HttpResponse<IOrderStatusModel[]>;

@Injectable({ providedIn: 'root' })
@Injectable({
  providedIn: 'root'
})
export class OrderStatusService {
  public resourceUrl = SERVER_API_URL + 'api/order-statuses';

  constructor(protected http: HttpClient) {}

  create(orderStatusModel: IOrderStatusModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderStatusModel);
    return this.http
      .post<IOrderStatusModel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderStatusModel: IOrderStatusModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderStatusModel);
    return this.http
      .put<IOrderStatusModel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderStatusModel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderStatusModel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderStatusModel: IOrderStatusModel): IOrderStatusModel {
    const copy: IOrderStatusModel = Object.assign({}, orderStatusModel, {
      dateCreated:
        orderStatusModel.dateCreated != null && orderStatusModel.dateCreated.isValid() ? orderStatusModel.dateCreated.toJSON() : null,
      dateUpdated:
        orderStatusModel.dateUpdated != null && orderStatusModel.dateUpdated.isValid() ? orderStatusModel.dateUpdated.toJSON() : null
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
      res.body.forEach((orderStatusModel: IOrderStatusModel) => {
        orderStatusModel.dateCreated = orderStatusModel.dateCreated != null ? moment(orderStatusModel.dateCreated) : null;
        orderStatusModel.dateUpdated = orderStatusModel.dateUpdated != null ? moment(orderStatusModel.dateUpdated) : null;
      });
    }
    return res;
  }
}
