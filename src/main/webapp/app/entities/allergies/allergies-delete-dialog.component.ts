import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllergies } from 'app/shared/model/allergies.model';
import { AllergiesService } from './allergies.service';

@Component({
  selector: 'jhi-allergies-delete-dialog',
  templateUrl: './allergies-delete-dialog.component.html'
})
export class AllergiesDeleteDialogComponent {
  allergies: IAllergies;

  constructor(protected allergiesService: AllergiesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allergiesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allergiesListModification',
        content: 'Deleted an allergies'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allergies-delete-popup',
  template: ''
})
export class AllergiesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allergies }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllergiesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.allergies = allergies;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/allergies', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/allergies', { outlets: { popup: null } }]);
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
