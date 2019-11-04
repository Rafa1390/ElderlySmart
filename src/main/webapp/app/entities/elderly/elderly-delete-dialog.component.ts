import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElderly } from 'app/shared/model/elderly.model';
import { ElderlyService } from './elderly.service';

@Component({
  selector: 'jhi-elderly-delete-dialog',
  templateUrl: './elderly-delete-dialog.component.html'
})
export class ElderlyDeleteDialogComponent {
  elderly: IElderly;

  constructor(protected elderlyService: ElderlyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.elderlyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'elderlyListModification',
        content: 'Deleted an elderly'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-elderly-delete-popup',
  template: ''
})
export class ElderlyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ elderly }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ElderlyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.elderly = elderly;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/elderly', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/elderly', { outlets: { popup: null } }]);
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
