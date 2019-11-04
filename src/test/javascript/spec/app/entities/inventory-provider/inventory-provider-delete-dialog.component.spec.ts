import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { InventoryProviderDeleteDialogComponent } from 'app/entities/inventory-provider/inventory-provider-delete-dialog.component';
import { InventoryProviderService } from 'app/entities/inventory-provider/inventory-provider.service';

describe('Component Tests', () => {
  describe('InventoryProvider Management Delete Component', () => {
    let comp: InventoryProviderDeleteDialogComponent;
    let fixture: ComponentFixture<InventoryProviderDeleteDialogComponent>;
    let service: InventoryProviderService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [InventoryProviderDeleteDialogComponent]
      })
        .overrideTemplate(InventoryProviderDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryProviderDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryProviderService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
