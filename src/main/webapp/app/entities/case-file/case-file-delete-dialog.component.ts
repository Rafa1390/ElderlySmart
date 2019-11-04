import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaseFile } from 'app/shared/model/case-file.model';
import { CaseFileService } from './case-file.service';

@Component({
  selector: 'jhi-case-file-delete-dialog',
  templateUrl: './case-file-delete-dialog.component.html'
})
export class CaseFileDeleteDialogComponent {
  caseFile: ICaseFile;

  constructor(protected caseFileService: CaseFileService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.caseFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'caseFileListModification',
        content: 'Deleted an caseFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-case-file-delete-popup',
  template: ''
})
export class CaseFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ caseFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CaseFileDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.caseFile = caseFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/case-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/case-file', { outlets: { popup: null } }]);
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
