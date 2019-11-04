import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { PharmacyHistoryDeleteDialogComponent } from 'app/entities/pharmacy-history/pharmacy-history-delete-dialog.component';
import { PharmacyHistoryService } from 'app/entities/pharmacy-history/pharmacy-history.service';

describe('Component Tests', () => {
  describe('PharmacyHistory Management Delete Component', () => {
    let comp: PharmacyHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<PharmacyHistoryDeleteDialogComponent>;
    let service: PharmacyHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PharmacyHistoryDeleteDialogComponent]
      })
        .overrideTemplate(PharmacyHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PharmacyHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacyHistoryService);
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
