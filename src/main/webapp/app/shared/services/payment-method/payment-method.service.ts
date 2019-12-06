import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentMethod } from 'app/shared/model/payment-method.model';

type EntityResponseType = HttpResponse<IPaymentMethod>;
type EntityArrayResponseType = HttpResponse<IPaymentMethod[]>;

@Injectable({ providedIn: 'root' })
@Injectable({
  providedIn: 'root'
})
export class PaymentMethodService {
  public resourceUrl = SERVER_API_URL + 'api/payment-methods';

  constructor(protected http: HttpClient) {}

  create(paymentMethodModel: IPaymentMethod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethodModel);
    return this.http
      .post<IPaymentMethod>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentMethodModel: IPaymentMethod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethodModel);
    return this.http
      .put<IPaymentMethod>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentMethod>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentMethod[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paymentMethodModel: IPaymentMethod): IPaymentMethod {
    const copy: IPaymentMethod = Object.assign({}, paymentMethodModel, {
      dateCreated:
        paymentMethodModel.dateCreated != null && paymentMethodModel.dateCreated.isValid() ? paymentMethodModel.dateCreated.toJSON() : null,
      dateUpdated:
        paymentMethodModel.dateUpdated != null && paymentMethodModel.dateUpdated.isValid() ? paymentMethodModel.dateUpdated.toJSON() : null
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
      res.body.forEach((paymentMethodModel: IPaymentMethod) => {
        paymentMethodModel.dateCreated = paymentMethodModel.dateCreated != null ? moment(paymentMethodModel.dateCreated) : null;
        paymentMethodModel.dateUpdated = paymentMethodModel.dateUpdated != null ? moment(paymentMethodModel.dateUpdated) : null;
      });
    }
    return res;
  }
}
