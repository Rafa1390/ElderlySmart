import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { ElderlyUpdateComponent } from 'app/entities/elderly/elderly-update.component';
import { ElderlyService } from 'app/entities/elderly/elderly.service';
import { Elderly } from 'app/shared/model/elderly.model';

describe('Component Tests', () => {
  describe('Elderly Management Update Component', () => {
    let comp: ElderlyUpdateComponent;
    let fixture: ComponentFixture<ElderlyUpdateComponent>;
    let service: ElderlyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ElderlyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ElderlyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElderlyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElderlyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Elderly(123);
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
        const entity = new Elderly();
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
