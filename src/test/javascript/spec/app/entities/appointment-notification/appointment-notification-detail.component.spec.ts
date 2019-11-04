import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { AppointmentNotificationDetailComponent } from 'app/entities/appointment-notification/appointment-notification-detail.component';
import { AppointmentNotification } from 'app/shared/model/appointment-notification.model';

describe('Component Tests', () => {
  describe('AppointmentNotification Management Detail Component', () => {
    let comp: AppointmentNotificationDetailComponent;
    let fixture: ComponentFixture<AppointmentNotificationDetailComponent>;
    const route = ({ data: of({ appointmentNotification: new AppointmentNotification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AppointmentNotificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AppointmentNotificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppointmentNotificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appointmentNotification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
