import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoodOrder } from 'app/shared/model/food-order.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FoodOrderService } from './food-order.service';
import { FoodOrderDeleteDialogComponent } from './food-order-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-food-order',
  templateUrl: './food-order.component.html'
})
export class FoodOrderComponent implements OnInit, OnDestroy {
  foodOrders: IFoodOrder[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  account: Account;

  constructor(
    protected foodOrderService: FoodOrderService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    if (this.account.authorities.includes('ROLE_USER')) {
      this.foodOrderService
        .queryBy(this.account.id, 'user', {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IFoodOrder[]>) => this.paginateFoodOrders(res.body, res.headers));
    } else if (this.account.authorities.length === 1 && this.account.authorities.includes('ROLE_VENDOR')) {
      this.foodOrderService
        .queryBy(this.account.id, 'vendor', {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IFoodOrder[]>) => this.paginateFoodOrders(res.body, res.headers));
    } else {
      this.foodOrderService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IFoodOrder[]>) => this.paginateFoodOrders(res.body, res.headers));
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/food-order'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/food-order',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });
    this.loadAll();
    this.registerChangeInFoodOrders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFoodOrder) {
    return item.id;
  }

  registerChangeInFoodOrders() {
    this.eventSubscriber = this.eventManager.subscribe('foodOrderListModification', () => this.loadAll());
  }

  delete(foodOrder: IFoodOrder) {
    const modalRef = this.modalService.open(FoodOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.foodOrder = foodOrder;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFoodOrders(data: IFoodOrder[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.foodOrders = data;
  }
}
