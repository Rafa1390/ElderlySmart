import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPharmacyHistory } from 'app/shared/model/pharmacy-history.model';
import { PharmacyHistoryService } from './pharmacy-history.service';

@Component({
  selector: 'jhi-pharmacy-history-delete-dialog',
  templateUrl: './pharmacy-history-delete-dialog.component.html'
})
export class PharmacyHistoryDeleteDialogComponent {
  pharmacyHistory: IPharmacyHistory;

  constructor(
    protected pharmacyHistoryService: PharmacyHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pharmacyHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pharmacyHistoryListModification',
        content: 'Deleted an pharmacyHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pharmacy-history-delete-popup',
  template: ''
})
export class PharmacyHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pharmacyHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PharmacyHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pharmacyHistory = pharmacyHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pharmacy-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pharmacy-history', { outlets: { popup: null } }]);
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
