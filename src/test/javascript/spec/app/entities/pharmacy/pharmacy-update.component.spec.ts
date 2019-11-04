import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { PharmacyUpdateComponent } from 'app/entities/pharmacy/pharmacy-update.component';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';
import { Pharmacy } from 'app/shared/model/pharmacy.model';

describe('Component Tests', () => {
  describe('Pharmacy Management Update Component', () => {
    let comp: PharmacyUpdateComponent;
    let fixture: ComponentFixture<PharmacyUpdateComponent>;
    let service: PharmacyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PharmacyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PharmacyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PharmacyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pharmacy(123);
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
        const entity = new Pharmacy();
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
