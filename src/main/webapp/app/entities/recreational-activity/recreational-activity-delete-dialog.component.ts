import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecreationalActivity } from 'app/shared/model/recreational-activity.model';
import { RecreationalActivityService } from './recreational-activity.service';

@Component({
  selector: 'jhi-recreational-activity-delete-dialog',
  templateUrl: './recreational-activity-delete-dialog.component.html'
})
export class RecreationalActivityDeleteDialogComponent {
  recreationalActivity: IRecreationalActivity;

  constructor(
    protected recreationalActivityService: RecreationalActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.recreationalActivityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'recreationalActivityListModification',
        content: 'Deleted an recreationalActivity'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-recreational-activity-delete-popup',
  template: ''
})
export class RecreationalActivityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recreationalActivity }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RecreationalActivityDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.recreationalActivity = recreationalActivity;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/recreational-activity', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/recreational-activity', { outlets: { popup: null } }]);
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
