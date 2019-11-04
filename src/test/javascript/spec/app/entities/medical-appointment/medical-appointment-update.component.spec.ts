import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { MedicalAppointmentUpdateComponent } from 'app/entities/medical-appointment/medical-appointment-update.component';
import { MedicalAppointmentService } from 'app/entities/medical-appointment/medical-appointment.service';
import { MedicalAppointment } from 'app/shared/model/medical-appointment.model';

describe('Component Tests', () => {
  describe('MedicalAppointment Management Update Component', () => {
    let comp: MedicalAppointmentUpdateComponent;
    let fixture: ComponentFixture<MedicalAppointmentUpdateComponent>;
    let service: MedicalAppointmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MedicalAppointmentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicalAppointmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalAppointmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicalAppointmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalAppointment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicalAppointment();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
