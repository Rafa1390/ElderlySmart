import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProviderHistory } from 'app/shared/model/provider-history.model';
import { ProviderHistoryService } from './provider-history.service';

@Component({
  selector: 'jhi-provider-history-delete-dialog',
  templateUrl: './provider-history-delete-dialog.component.html'
})
export class ProviderHistoryDeleteDialogComponent {
  providerHistory: IProviderHistory;

  constructor(
    protected providerHistoryService: ProviderHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.providerHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'providerHistoryListModification',
        content: 'Deleted an providerHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-provider-history-delete-popup',
  template: ''
})
export class ProviderHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ providerHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProviderHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.providerHistory = providerHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/provider-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/provider-history', { outlets: { popup: null } }]);
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
