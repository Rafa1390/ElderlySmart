import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { PharmacyService } from './pharmacy.service';

@Component({
  selector: 'jhi-pharmacy-delete-dialog',
  templateUrl: './pharmacy-delete-dialog.component.html'
})
export class PharmacyDeleteDialogComponent {
  pharmacy: IPharmacy;

  constructor(protected pharmacyService: PharmacyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pharmacyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pharmacyListModification',
        content: 'Deleted an pharmacy'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pharmacy-delete-popup',
  template: ''
})
export class PharmacyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pharmacy }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PharmacyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pharmacy = pharmacy;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pharmacy', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pharmacy', { outlets: { popup: null } }]);
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
