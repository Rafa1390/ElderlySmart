import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { PharmacyDeleteDialogComponent } from 'app/entities/pharmacy/pharmacy-delete-dialog.component';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';

describe('Component Tests', () => {
  describe('Pharmacy Management Delete Component', () => {
    let comp: PharmacyDeleteDialogComponent;
    let fixture: ComponentFixture<PharmacyDeleteDialogComponent>;
    let service: PharmacyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PharmacyDeleteDialogComponent]
      })
        .overrideTemplate(PharmacyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PharmacyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacyService);
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
