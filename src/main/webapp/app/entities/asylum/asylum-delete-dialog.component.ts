import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAsylum } from 'app/shared/model/asylum.model';
import { AsylumService } from './asylum.service';

@Component({
  selector: 'jhi-asylum-delete-dialog',
  templateUrl: './asylum-delete-dialog.component.html'
})
export class AsylumDeleteDialogComponent {
  asylum: IAsylum;

  constructor(protected asylumService: AsylumService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.asylumService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'asylumListModification',
        content: 'Deleted an asylum'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-asylum-delete-popup',
  template: ''
})
export class AsylumDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ asylum }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AsylumDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.asylum = asylum;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/asylum', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/asylum', { outlets: { popup: null } }]);
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
