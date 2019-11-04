import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';
import { MedicalAppointmentService } from './medical-appointment.service';

@Component({
  selector: 'jhi-medical-appointment-delete-dialog',
  templateUrl: './medical-appointment-delete-dialog.component.html'
})
export class MedicalAppointmentDeleteDialogComponent {
  medicalAppointment: IMedicalAppointment;

  constructor(
    protected medicalAppointmentService: MedicalAppointmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicalAppointmentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'medicalAppointmentListModification',
        content: 'Deleted an medicalAppointment'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-medical-appointment-delete-popup',
  template: ''
})
export class MedicalAppointmentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicalAppointment }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MedicalAppointmentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.medicalAppointment = medicalAppointment;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/medical-appointment', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/medical-appointment', { outlets: { popup: null } }]);
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
