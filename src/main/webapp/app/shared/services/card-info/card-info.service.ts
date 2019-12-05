import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICardInfoModel } from 'app/shared/model/card-info.model';

type EntityResponseType = HttpResponse<ICardInfoModel>;
type EntityArrayResponseType = HttpResponse<ICardInfoModel[]>;

@Injectable({ providedIn: 'root' })
@Injectable({
  providedIn: 'root'
})
export class CardInfoService {
  public resourceUrl = SERVER_API_URL + 'api/card-infos';

  constructor(protected http: HttpClient) {}

  create(cardInfoModel: ICardInfoModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cardInfoModel);
    return this.http
      .post<ICardInfoModel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cardInfoModel: ICardInfoModel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cardInfoModel);
    return this.http
      .put<ICardInfoModel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICardInfoModel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICardInfoModel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cardInfoModel: ICardInfoModel): ICardInfoModel {
    const copy: ICardInfoModel = Object.assign({}, cardInfoModel, {
      dateCreated: cardInfoModel.dateCreated != null && cardInfoModel.dateCreated.isValid() ? cardInfoModel.dateCreated.toJSON() : null,
      dateUpdated: cardInfoModel.dateUpdated != null && cardInfoModel.dateUpdated.isValid() ? cardInfoModel.dateUpdated.toJSON() : null
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
      res.body.forEach((cardInfoModel: ICardInfoModel) => {
        cardInfoModel.dateCreated = cardInfoModel.dateCreated != null ? moment(cardInfoModel.dateCreated) : null;
        cardInfoModel.dateUpdated = cardInfoModel.dateUpdated != null ? moment(cardInfoModel.dateUpdated) : null;
      });
    }
    return res;
  }
}
