import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';
import { InventoryPharmacyService } from './inventory-pharmacy.service';

@Component({
  selector: 'jhi-inventory-pharmacy-delete-dialog',
  templateUrl: './inventory-pharmacy-delete-dialog.component.html'
})
export class InventoryPharmacyDeleteDialogComponent {
  inventoryPharmacy: IInventoryPharmacy;

  constructor(
    protected inventoryPharmacyService: InventoryPharmacyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inventoryPharmacyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inventoryPharmacyListModification',
        content: 'Deleted an inventoryPharmacy'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inventory-pharmacy-delete-popup',
  template: ''
})
export class InventoryPharmacyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventoryPharmacy }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InventoryPharmacyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inventoryPharmacy = inventoryPharmacy;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inventory-pharmacy', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inventory-pharmacy', { outlets: { popup: null } }]);
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
