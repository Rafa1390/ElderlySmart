import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDonationHistory } from 'app/shared/model/donation-history.model';
import { DonationHistoryService } from './donation-history.service';

@Component({
  selector: 'jhi-donation-history-delete-dialog',
  templateUrl: './donation-history-delete-dialog.component.html'
})
export class DonationHistoryDeleteDialogComponent {
  donationHistory: IDonationHistory;

  constructor(
    protected donationHistoryService: DonationHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.donationHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'donationHistoryListModification',
        content: 'Deleted an donationHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-donation-history-delete-popup',
  template: ''
})
export class DonationHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ donationHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DonationHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.donationHistory = donationHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/donation-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/donation-history', { outlets: { popup: null } }]);
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
