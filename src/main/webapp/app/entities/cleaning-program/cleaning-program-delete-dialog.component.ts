import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICleaningProgram } from 'app/shared/model/cleaning-program.model';
import { CleaningProgramService } from './cleaning-program.service';

@Component({
  selector: 'jhi-cleaning-program-delete-dialog',
  templateUrl: './cleaning-program-delete-dialog.component.html'
})
export class CleaningProgramDeleteDialogComponent {
  cleaningProgram: ICleaningProgram;

  constructor(
    protected cleaningProgramService: CleaningProgramService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cleaningProgramService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cleaningProgramListModification',
        content: 'Deleted an cleaningProgram'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cleaning-program-delete-popup',
  template: ''
})
export class CleaningProgramDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cleaningProgram }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CleaningProgramDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cleaningProgram = cleaningProgram;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cleaning-program', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cleaning-program', { outlets: { popup: null } }]);
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
