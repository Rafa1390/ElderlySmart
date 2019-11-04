import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPathologies } from 'app/shared/model/pathologies.model';
import { PathologiesService } from './pathologies.service';

@Component({
  selector: 'jhi-pathologies-delete-dialog',
  templateUrl: './pathologies-delete-dialog.component.html'
})
export class PathologiesDeleteDialogComponent {
  pathologies: IPathologies;

  constructor(
    protected pathologiesService: PathologiesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pathologiesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pathologiesListModification',
        content: 'Deleted an pathologies'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pathologies-delete-popup',
  template: ''
})
export class PathologiesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pathologies }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PathologiesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pathologies = pathologies;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pathologies', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pathologies', { outlets: { popup: null } }]);
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
