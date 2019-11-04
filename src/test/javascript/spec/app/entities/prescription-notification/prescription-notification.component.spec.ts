import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { PrescriptionNotificationComponent } from 'app/entities/prescription-notification/prescription-notification.component';
import { PrescriptionNotificationService } from 'app/entities/prescription-notification/prescription-notification.service';
import { PrescriptionNotification } from 'app/shared/model/prescription-notification.model';

describe('Component Tests', () => {
  describe('PrescriptionNotification Management Component', () => {
    let comp: PrescriptionNotificationComponent;
    let fixture: ComponentFixture<PrescriptionNotificationComponent>;
    let service: PrescriptionNotificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PrescriptionNotificationComponent],
        providers: []
      })
        .overrideTemplate(PrescriptionNotificationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrescriptionNotificationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrescriptionNotificationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PrescriptionNotification(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prescriptionNotifications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
