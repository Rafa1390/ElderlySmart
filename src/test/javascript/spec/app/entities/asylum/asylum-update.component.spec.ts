import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { AsylumUpdateComponent } from 'app/entities/asylum/asylum-update.component';
import { AsylumService } from 'app/entities/asylum/asylum.service';
import { Asylum } from 'app/shared/model/asylum.model';

describe('Component Tests', () => {
  describe('Asylum Management Update Component', () => {
    let comp: AsylumUpdateComponent;
    let fixture: ComponentFixture<AsylumUpdateComponent>;
    let service: AsylumService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AsylumUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AsylumUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AsylumUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AsylumService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Asylum(123);
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
        const entity = new Asylum();
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
