import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { MedicalAppointmentDetailComponent } from 'app/entities/medical-appointment/medical-appointment-detail.component';
import { MedicalAppointment } from 'app/shared/model/medical-appointment.model';

describe('Component Tests', () => {
  describe('MedicalAppointment Management Detail Component', () => {
    let comp: MedicalAppointmentDetailComponent;
    let fixture: ComponentFixture<MedicalAppointmentDetailComponent>;
    const route = ({ data: of({ medicalAppointment: new MedicalAppointment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MedicalAppointmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MedicalAppointmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalAppointmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalAppointment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
