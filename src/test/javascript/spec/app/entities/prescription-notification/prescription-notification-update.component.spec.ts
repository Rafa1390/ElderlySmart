import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { PrescriptionNotificationUpdateComponent } from 'app/entities/prescription-notification/prescription-notification-update.component';
import { PrescriptionNotificationService } from 'app/entities/prescription-notification/prescription-notification.service';
import { PrescriptionNotification } from 'app/shared/model/prescription-notification.model';

describe('Component Tests', () => {
  describe('PrescriptionNotification Management Update Component', () => {
    let comp: PrescriptionNotificationUpdateComponent;
    let fixture: ComponentFixture<PrescriptionNotificationUpdateComponent>;
    let service: PrescriptionNotificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PrescriptionNotificationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrescriptionNotificationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrescriptionNotificationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrescriptionNotificationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrescriptionNotification(123);
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
        const entity = new PrescriptionNotification();
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
