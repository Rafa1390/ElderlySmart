import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicationSchedule } from 'app/shared/model/medication-schedule.model';
import { MedicationScheduleService } from './medication-schedule.service';

@Component({
  selector: 'jhi-medication-schedule-delete-dialog',
  templateUrl: './medication-schedule-delete-dialog.component.html'
})
export class MedicationScheduleDeleteDialogComponent {
  medicationSchedule: IMedicationSchedule;

  constructor(
    protected medicationScheduleService: MedicationScheduleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.medicationScheduleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'medicationScheduleListModification',
        content: 'Deleted an medicationSchedule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-medication-schedule-delete-popup',
  template: ''
})
export class MedicationScheduleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicationSchedule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MedicationScheduleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.medicationSchedule = medicationSchedule;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/medication-schedule', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/medication-schedule', { outlets: { popup: null } }]);
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
