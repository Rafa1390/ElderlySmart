import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { RecreationalActivityUpdateComponent } from 'app/entities/recreational-activity/recreational-activity-update.component';
import { RecreationalActivityService } from 'app/entities/recreational-activity/recreational-activity.service';
import { RecreationalActivity } from 'app/shared/model/recreational-activity.model';

describe('Component Tests', () => {
  describe('RecreationalActivity Management Update Component', () => {
    let comp: RecreationalActivityUpdateComponent;
    let fixture: ComponentFixture<RecreationalActivityUpdateComponent>;
    let service: RecreationalActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [RecreationalActivityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RecreationalActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecreationalActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecreationalActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RecreationalActivity(123);
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
        const entity = new RecreationalActivity();
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
