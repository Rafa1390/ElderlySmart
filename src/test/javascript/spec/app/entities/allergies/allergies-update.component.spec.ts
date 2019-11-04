import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { AllergiesUpdateComponent } from 'app/entities/allergies/allergies-update.component';
import { AllergiesService } from 'app/entities/allergies/allergies.service';
import { Allergies } from 'app/shared/model/allergies.model';

describe('Component Tests', () => {
  describe('Allergies Management Update Component', () => {
    let comp: AllergiesUpdateComponent;
    let fixture: ComponentFixture<AllergiesUpdateComponent>;
    let service: AllergiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AllergiesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AllergiesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllergiesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllergiesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Allergies(123);
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
        const entity = new Allergies();
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
