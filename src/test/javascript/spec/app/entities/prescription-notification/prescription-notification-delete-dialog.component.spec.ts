import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { PrescriptionNotificationDeleteDialogComponent } from 'app/entities/prescription-notification/prescription-notification-delete-dialog.component';
import { PrescriptionNotificationService } from 'app/entities/prescription-notification/prescription-notification.service';

describe('Component Tests', () => {
  describe('PrescriptionNotification Management Delete Component', () => {
    let comp: PrescriptionNotificationDeleteDialogComponent;
    let fixture: ComponentFixture<PrescriptionNotificationDeleteDialogComponent>;
    let service: PrescriptionNotificationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PrescriptionNotificationDeleteDialogComponent]
      })
        .overrideTemplate(PrescriptionNotificationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrescriptionNotificationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrescriptionNotificationService);
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
