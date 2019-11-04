import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { FuneralPackagesUpdateComponent } from 'app/entities/funeral-packages/funeral-packages-update.component';
import { FuneralPackagesService } from 'app/entities/funeral-packages/funeral-packages.service';
import { FuneralPackages } from 'app/shared/model/funeral-packages.model';

describe('Component Tests', () => {
  describe('FuneralPackages Management Update Component', () => {
    let comp: FuneralPackagesUpdateComponent;
    let fixture: ComponentFixture<FuneralPackagesUpdateComponent>;
    let service: FuneralPackagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FuneralPackagesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FuneralPackagesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuneralPackagesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuneralPackagesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FuneralPackages(123);
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
        const entity = new FuneralPackages();
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
