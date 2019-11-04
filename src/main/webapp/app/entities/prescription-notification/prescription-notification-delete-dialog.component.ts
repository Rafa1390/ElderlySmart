import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrescriptionNotification } from 'app/shared/model/prescription-notification.model';
import { PrescriptionNotificationService } from './prescription-notification.service';

@Component({
  selector: 'jhi-prescription-notification-delete-dialog',
  templateUrl: './prescription-notification-delete-dialog.component.html'
})
export class PrescriptionNotificationDeleteDialogComponent {
  prescriptionNotification: IPrescriptionNotification;

  constructor(
    protected prescriptionNotificationService: PrescriptionNotificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.prescriptionNotificationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'prescriptionNotificationListModification',
        content: 'Deleted an prescriptionNotification'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-prescription-notification-delete-popup',
  template: ''
})
export class PrescriptionNotificationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prescriptionNotification }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PrescriptionNotificationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.prescriptionNotification = prescriptionNotification;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/prescription-notification', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/prescription-notification', { outlets: { popup: null } }]);
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
