import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { MedicationScheduleComponent } from 'app/entities/medication-schedule/medication-schedule.component';
import { MedicationScheduleService } from 'app/entities/medication-schedule/medication-schedule.service';
import { MedicationSchedule } from 'app/shared/model/medication-schedule.model';

describe('Component Tests', () => {
  describe('MedicationSchedule Management Component', () => {
    let comp: MedicationScheduleComponent;
    let fixture: ComponentFixture<MedicationScheduleComponent>;
    let service: MedicationScheduleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MedicationScheduleComponent],
        providers: []
      })
        .overrideTemplate(MedicationScheduleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicationScheduleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedicationScheduleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MedicationSchedule(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medicationSchedules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
