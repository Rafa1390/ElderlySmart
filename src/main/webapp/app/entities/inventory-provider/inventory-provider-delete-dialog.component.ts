import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryProvider } from 'app/shared/model/inventory-provider.model';
import { InventoryProviderService } from './inventory-provider.service';

@Component({
  selector: 'jhi-inventory-provider-delete-dialog',
  templateUrl: './inventory-provider-delete-dialog.component.html'
})
export class InventoryProviderDeleteDialogComponent {
  inventoryProvider: IInventoryProvider;

  constructor(
    protected inventoryProviderService: InventoryProviderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inventoryProviderService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inventoryProviderListModification',
        content: 'Deleted an inventoryProvider'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inventory-provider-delete-popup',
  template: ''
})
export class InventoryProviderDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventoryProvider }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InventoryProviderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inventoryProvider = inventoryProvider;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inventory-provider', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inventory-provider', { outlets: { popup: null } }]);
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
