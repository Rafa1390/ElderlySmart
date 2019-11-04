import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppointmentNotification } from 'app/shared/model/appointment-notification.model';
import { AppointmentNotificationService } from './appointment-notification.service';

@Component({
  selector: 'jhi-appointment-notification-delete-dialog',
  templateUrl: './appointment-notification-delete-dialog.component.html'
})
export class AppointmentNotificationDeleteDialogComponent {
  appointmentNotification: IAppointmentNotification;

  constructor(
    protected appointmentNotificationService: AppointmentNotificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.appointmentNotificationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'appointmentNotificationListModification',
        content: 'Deleted an appointmentNotification'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-appointment-notification-delete-popup',
  template: ''
})
export class AppointmentNotificationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appointmentNotification }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AppointmentNotificationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.appointmentNotification = appointmentNotification;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/appointment-notification', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/appointment-notification', { outlets: { popup: null } }]);
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
