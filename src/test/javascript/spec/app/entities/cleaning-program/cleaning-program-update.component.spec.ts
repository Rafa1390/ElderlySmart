import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { CleaningProgramUpdateComponent } from 'app/entities/cleaning-program/cleaning-program-update.component';
import { CleaningProgramService } from 'app/entities/cleaning-program/cleaning-program.service';
import { CleaningProgram } from 'app/shared/model/cleaning-program.model';

describe('Component Tests', () => {
  describe('CleaningProgram Management Update Component', () => {
    let comp: CleaningProgramUpdateComponent;
    let fixture: ComponentFixture<CleaningProgramUpdateComponent>;
    let service: CleaningProgramService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [CleaningProgramUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CleaningProgramUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CleaningProgramUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CleaningProgramService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CleaningProgram(123);
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
        const entity = new CleaningProgram();
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
