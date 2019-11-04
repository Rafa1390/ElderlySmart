import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { MedicationScheduleDetailComponent } from 'app/entities/medication-schedule/medication-schedule-detail.component';
import { MedicationSchedule } from 'app/shared/model/medication-schedule.model';

describe('Component Tests', () => {
  describe('MedicationSchedule Management Detail Component', () => {
    let comp: MedicationScheduleDetailComponent;
    let fixture: ComponentFixture<MedicationScheduleDetailComponent>;
    const route = ({ data: of({ medicationSchedule: new MedicationSchedule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MedicationScheduleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicationScheduleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicationScheduleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicationSchedule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
