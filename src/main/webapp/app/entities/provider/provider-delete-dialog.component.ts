import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProvider } from 'app/shared/model/provider.model';
import { ProviderService } from './provider.service';

@Component({
  selector: 'jhi-provider-delete-dialog',
  templateUrl: './provider-delete-dialog.component.html'
})
export class ProviderDeleteDialogComponent {
  provider: IProvider;

  constructor(protected providerService: ProviderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.providerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'providerListModification',
        content: 'Deleted an provider'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-provider-delete-popup',
  template: ''
})
export class ProviderDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ provider }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProviderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.provider = provider;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/provider', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/provider', { outlets: { popup: null } }]);
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
