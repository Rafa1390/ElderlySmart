import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { AppointmentNotificationUpdateComponent } from 'app/entities/appointment-notification/appointment-notification-update.component';
import { AppointmentNotificationService } from 'app/entities/appointment-notification/appointment-notification.service';
import { AppointmentNotification } from 'app/shared/model/appointment-notification.model';

describe('Component Tests', () => {
  describe('AppointmentNotification Management Update Component', () => {
    let comp: AppointmentNotificationUpdateComponent;
    let fixture: ComponentFixture<AppointmentNotificationUpdateComponent>;
    let service: AppointmentNotificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AppointmentNotificationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AppointmentNotificationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppointmentNotificationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppointmentNotificationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AppointmentNotification(123);
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
        const entity = new AppointmentNotification();
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
