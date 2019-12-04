import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';

@Component({
  templateUrl: './menu-delete-dialog.component.html'
})
export class MenuDeleteDialogComponent {
  menu: IMenu;

  constructor(protected menuService: MenuService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.menuService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'menuListModification',
        content: 'Deleted an menu'
      });
      this.activeModal.dismiss(true);
    });
  }
}
