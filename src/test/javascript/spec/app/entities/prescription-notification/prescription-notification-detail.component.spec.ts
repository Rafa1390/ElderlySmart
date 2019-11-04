import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { PrescriptionNotificationDetailComponent } from 'app/entities/prescription-notification/prescription-notification-detail.component';
import { PrescriptionNotification } from 'app/shared/model/prescription-notification.model';

describe('Component Tests', () => {
  describe('PrescriptionNotification Management Detail Component', () => {
    let comp: PrescriptionNotificationDetailComponent;
    let fixture: ComponentFixture<PrescriptionNotificationDetailComponent>;
    const route = ({ data: of({ prescriptionNotification: new PrescriptionNotification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PrescriptionNotificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrescriptionNotificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrescriptionNotificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prescriptionNotification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
