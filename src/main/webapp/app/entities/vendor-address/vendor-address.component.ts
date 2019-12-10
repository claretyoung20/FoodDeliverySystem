import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVendorAddress } from 'app/shared/model/vendor-address.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VendorAddressService } from './vendor-address.service';
import { VendorAddressDeleteDialogComponent } from './vendor-address-delete-dialog.component';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-vendor-address',
  templateUrl: './vendor-address.component.html'
})
export class VendorAddressComponent implements OnInit, OnDestroy {
  vendorAddresses: IVendorAddress[];
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
    protected vendorAddressService: VendorAddressService,
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
    if (this.account.authorities.length === 1 && this.account.authorities.includes('ROLE_VENDOR')) {
      this.vendorAddressService
        .queryById(this.account.id, {
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IVendorAddress[]>) => this.paginateVendorAddresses(res.body, res.headers));
    } else {
      this.vendorAddressService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IVendorAddress[]>) => this.paginateVendorAddresses(res.body, res.headers));
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/vendor-address'], {
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
      '/vendor-address',
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
    this.registerChangeInVendorAddresses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVendorAddress) {
    return item.id;
  }

  registerChangeInVendorAddresses() {
    this.eventSubscriber = this.eventManager.subscribe('vendorAddressListModification', () => this.loadAll());
  }

  delete(vendorAddress: IVendorAddress) {
    const modalRef = this.modalService.open(VendorAddressDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vendorAddress = vendorAddress;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVendorAddresses(data: IVendorAddress[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.vendorAddresses = data;
  }
}
