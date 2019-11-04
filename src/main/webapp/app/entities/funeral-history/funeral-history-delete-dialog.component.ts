import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFuneralHistory } from 'app/shared/model/funeral-history.model';
import { FuneralHistoryService } from './funeral-history.service';

@Component({
  selector: 'jhi-funeral-history-delete-dialog',
  templateUrl: './funeral-history-delete-dialog.component.html'
})
export class FuneralHistoryDeleteDialogComponent {
  funeralHistory: IFuneralHistory;

  constructor(
    protected funeralHistoryService: FuneralHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.funeralHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'funeralHistoryListModification',
        content: 'Deleted an funeralHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-funeral-history-delete-popup',
  template: ''
})
export class FuneralHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ funeralHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FuneralHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.funeralHistory = funeralHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/funeral-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/funeral-history', { outlets: { popup: null } }]);
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
