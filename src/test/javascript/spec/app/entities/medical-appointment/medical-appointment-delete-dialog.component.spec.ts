import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { MedicalAppointmentDeleteDialogComponent } from 'app/entities/medical-appointment/medical-appointment-delete-dialog.component';
import { MedicalAppointmentService } from 'app/entities/medical-appointment/medical-appointment.service';

describe('Component Tests', () => {
  describe('MedicalAppointment Management Delete Component', () => {
    let comp: MedicalAppointmentDeleteDialogComponent;
    let fixture: ComponentFixture<MedicalAppointmentDeleteDialogComponent>;
    let service: MedicalAppointmentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MedicalAppointmentDeleteDialogComponent]
      })
        .overrideTemplate(MedicalAppointmentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalAppointmentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalAppointmentService);
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
