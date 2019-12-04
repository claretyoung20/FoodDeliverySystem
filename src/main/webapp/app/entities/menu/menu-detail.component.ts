import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IMenu } from 'app/shared/model/menu.model';

@Component({
  selector: 'jhi-menu-detail',
  templateUrl: './menu-detail.component.html'
})
export class MenuDetailComponent implements OnInit {
  menu: IMenu;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ menu }) => {
      this.menu = menu;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
