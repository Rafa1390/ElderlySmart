import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFuneralPackages } from 'app/shared/model/funeral-packages.model';
import { FuneralPackagesService } from './funeral-packages.service';

@Component({
  selector: 'jhi-funeral-packages-delete-dialog',
  templateUrl: './funeral-packages-delete-dialog.component.html'
})
export class FuneralPackagesDeleteDialogComponent {
  funeralPackages: IFuneralPackages;

  constructor(
    protected funeralPackagesService: FuneralPackagesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.funeralPackagesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'funeralPackagesListModification',
        content: 'Deleted an funeralPackages'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-funeral-packages-delete-popup',
  template: ''
})
export class FuneralPackagesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ funeralPackages }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FuneralPackagesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.funeralPackages = funeralPackages;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/funeral-packages', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/funeral-packages', { outlets: { popup: null } }]);
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
