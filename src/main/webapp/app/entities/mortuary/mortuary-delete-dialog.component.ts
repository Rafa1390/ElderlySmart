import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMortuary } from 'app/shared/model/mortuary.model';
import { MortuaryService } from './mortuary.service';

@Component({
  selector: 'jhi-mortuary-delete-dialog',
  templateUrl: './mortuary-delete-dialog.component.html'
})
export class MortuaryDeleteDialogComponent {
  mortuary: IMortuary;

  constructor(protected mortuaryService: MortuaryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.mortuaryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'mortuaryListModification',
        content: 'Deleted an mortuary'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-mortuary-delete-popup',
  template: ''
})
export class MortuaryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ mortuary }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MortuaryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.mortuary = mortuary;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/mortuary', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/mortuary', { outlets: { popup: null } }]);
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
