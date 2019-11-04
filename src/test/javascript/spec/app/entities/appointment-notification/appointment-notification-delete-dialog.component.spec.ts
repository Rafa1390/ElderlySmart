import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { AppointmentNotificationDeleteDialogComponent } from 'app/entities/appointment-notification/appointment-notification-delete-dialog.component';
import { AppointmentNotificationService } from 'app/entities/appointment-notification/appointment-notification.service';

describe('Component Tests', () => {
  describe('AppointmentNotification Management Delete Component', () => {
    let comp: AppointmentNotificationDeleteDialogComponent;
    let fixture: ComponentFixture<AppointmentNotificationDeleteDialogComponent>;
    let service: AppointmentNotificationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AppointmentNotificationDeleteDialogComponent]
      })
        .overrideTemplate(AppointmentNotificationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppointmentNotificationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppointmentNotificationService);
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
