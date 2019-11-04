import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamily } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';

@Component({
  selector: 'jhi-family-delete-dialog',
  templateUrl: './family-delete-dialog.component.html'
})
export class FamilyDeleteDialogComponent {
  family: IFamily;

  constructor(protected familyService: FamilyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.familyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'familyListModification',
        content: 'Deleted an family'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-family-delete-popup',
  template: ''
})
export class FamilyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ family }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FamilyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.family = family;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/family', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/family', { outlets: { popup: null } }]);
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
