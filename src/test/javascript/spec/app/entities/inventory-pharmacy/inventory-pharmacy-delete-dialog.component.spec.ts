import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { InventoryPharmacyDeleteDialogComponent } from 'app/entities/inventory-pharmacy/inventory-pharmacy-delete-dialog.component';
import { InventoryPharmacyService } from 'app/entities/inventory-pharmacy/inventory-pharmacy.service';

describe('Component Tests', () => {
  describe('InventoryPharmacy Management Delete Component', () => {
    let comp: InventoryPharmacyDeleteDialogComponent;
    let fixture: ComponentFixture<InventoryPharmacyDeleteDialogComponent>;
    let service: InventoryPharmacyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [InventoryPharmacyDeleteDialogComponent]
      })
        .overrideTemplate(InventoryPharmacyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryPharmacyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryPharmacyService);
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
