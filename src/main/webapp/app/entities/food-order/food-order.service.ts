import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFoodOrder } from 'app/shared/model/food-order.model';

type EntityResponseType = HttpResponse<IFoodOrder>;
type EntityArrayResponseType = HttpResponse<IFoodOrder[]>;

@Injectable({ providedIn: 'root' })
export class FoodOrderService {
  public resourceUrl = SERVER_API_URL + 'api/food-orders';

  constructor(protected http: HttpClient) {}

  create(foodOrder: IFoodOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(foodOrder);
    return this.http
      .post<IFoodOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(foodOrder: IFoodOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(foodOrder);
    return this.http
      .put<IFoodOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFoodOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFoodOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryBy(id, user, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    const url = `${this.resourceUrl}/${user}/${id}`;
    return this.http
      .get<IFoodOrder[]>(url, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(foodOrder: IFoodOrder): IFoodOrder {
    const copy: IFoodOrder = Object.assign({}, foodOrder, {
      dateCreated: foodOrder.dateCreated != null && foodOrder.dateCreated.isValid() ? foodOrder.dateCreated.toJSON() : null,
      dateUpdated: foodOrder.dateUpdated != null && foodOrder.dateUpdated.isValid() ? foodOrder.dateUpdated.toJSON() : null
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
      res.body.forEach((foodOrder: IFoodOrder) => {
        foodOrder.dateCreated = foodOrder.dateCreated != null ? moment(foodOrder.dateCreated) : null;
        foodOrder.dateUpdated = foodOrder.dateUpdated != null ? moment(foodOrder.dateUpdated) : null;
      });
    }
    return res;
  }
}
