import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { MedicalAppointmentComponent } from 'app/entities/medical-appointment/medical-appointment.component';
import { MedicalAppointmentService } from 'app/entities/medical-appointment/medical-appointment.service';
import { MedicalAppointment } from 'app/shared/model/medical-appointment.model';

describe('Component Tests', () => {
  describe('MedicalAppointment Management Component', () => {
    let comp: MedicalAppointmentComponent;
    let fixture: ComponentFixture<MedicalAppointmentComponent>;
    let service: MedicalAppointmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MedicalAppointmentComponent],
        providers: []
      })
        .overrideTemplate(MedicalAppointmentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalAppointmentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalAppointmentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicalAppointment(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicalAppointments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
