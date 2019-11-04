import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { PharmacyHistoryUpdateComponent } from 'app/entities/pharmacy-history/pharmacy-history-update.component';
import { PharmacyHistoryService } from 'app/entities/pharmacy-history/pharmacy-history.service';
import { PharmacyHistory } from 'app/shared/model/pharmacy-history.model';

describe('Component Tests', () => {
  describe('PharmacyHistory Management Update Component', () => {
    let comp: PharmacyHistoryUpdateComponent;
    let fixture: ComponentFixture<PharmacyHistoryUpdateComponent>;
    let service: PharmacyHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PharmacyHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PharmacyHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PharmacyHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacyHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PharmacyHistory(123);
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
        const entity = new PharmacyHistory();
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
