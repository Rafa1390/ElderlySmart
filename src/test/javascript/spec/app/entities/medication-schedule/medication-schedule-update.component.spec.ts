import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { MedicationScheduleUpdateComponent } from 'app/entities/medication-schedule/medication-schedule-update.component';
import { MedicationScheduleService } from 'app/entities/medication-schedule/medication-schedule.service';
import { MedicationSchedule } from 'app/shared/model/medication-schedule.model';

describe('Component Tests', () => {
  describe('MedicationSchedule Management Update Component', () => {
    let comp: MedicationScheduleUpdateComponent;
    let fixture: ComponentFixture<MedicationScheduleUpdateComponent>;
    let service: MedicationScheduleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MedicationScheduleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MedicationScheduleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicationScheduleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicationScheduleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MedicationSchedule(123);
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
        const entity = new MedicationSchedule();
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
