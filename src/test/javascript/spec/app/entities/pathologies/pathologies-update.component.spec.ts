import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { PathologiesUpdateComponent } from 'app/entities/pathologies/pathologies-update.component';
import { PathologiesService } from 'app/entities/pathologies/pathologies.service';
import { Pathologies } from 'app/shared/model/pathologies.model';

describe('Component Tests', () => {
  describe('Pathologies Management Update Component', () => {
    let comp: PathologiesUpdateComponent;
    let fixture: ComponentFixture<PathologiesUpdateComponent>;
    let service: PathologiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PathologiesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PathologiesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PathologiesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PathologiesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pathologies(123);
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
        const entity = new Pathologies();
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
