import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodMenu } from 'app/shared/model/food-menu.model';
import { FoodMenuService } from './food-menu.service';

@Component({
  selector: 'jhi-food-menu-delete-dialog',
  templateUrl: './food-menu-delete-dialog.component.html'
})
export class FoodMenuDeleteDialogComponent {
  foodMenu: IFoodMenu;

  constructor(protected foodMenuService: FoodMenuService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.foodMenuService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'foodMenuListModification',
        content: 'Deleted an foodMenu'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-food-menu-delete-popup',
  template: ''
})
export class FoodMenuDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ foodMenu }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FoodMenuDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.foodMenu = foodMenu;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/food-menu', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/food-menu', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
