import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMenu } from 'app/shared/model/menu.model';
import { ICart } from 'app/shared/model/cart.model';

type EntityResponseType = HttpResponse<IMenu>;
type EntityArrayResponseType = HttpResponse<IMenu[]>;

@Injectable({ providedIn: 'root' })
export class MenuService {
  public resourceUrl = SERVER_API_URL + 'api/menus';

  constructor(protected http: HttpClient) {}

  create(menu: IMenu): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(menu);
    return this.http
      .post<IMenu>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(menu: IMenu): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(menu);
    return this.http
      .put<IMenu>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMenu>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMenu[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(menu: IMenu): IMenu {
    const copy: IMenu = Object.assign({}, menu, {
      dateCreated: menu.dateCreated != null && menu.dateCreated.isValid() ? menu.dateCreated.toJSON() : null,
      dateUpdated: menu.dateUpdated != null && menu.dateUpdated.isValid() ? menu.dateUpdated.toJSON() : null
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
      res.body.forEach((menu: IMenu) => {
        menu.dateCreated = menu.dateCreated != null ? moment(menu.dateCreated) : null;
        menu.dateUpdated = menu.dateUpdated != null ? moment(menu.dateUpdated) : null;
      });
    }
    return res;
  }

  /* get by user id*/
  queryById(id: any, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    const url = `${this.resourceUrl}/user/${id}`;
    return this.http
      .get<ICart[]>(url, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
}
