import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { AppointmentNotificationComponent } from 'app/entities/appointment-notification/appointment-notification.component';
import { AppointmentNotificationService } from 'app/entities/appointment-notification/appointment-notification.service';
import { AppointmentNotification } from 'app/shared/model/appointment-notification.model';

describe('Component Tests', () => {
  describe('AppointmentNotification Management Component', () => {
    let comp: AppointmentNotificationComponent;
    let fixture: ComponentFixture<AppointmentNotificationComponent>;
    let service: AppointmentNotificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AppointmentNotificationComponent],
        providers: []
      })
        .overrideTemplate(AppointmentNotificationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppointmentNotificationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppointmentNotificationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AppointmentNotification(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.appointmentNotifications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
