import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { FuneralHistoryUpdateComponent } from 'app/entities/funeral-history/funeral-history-update.component';
import { FuneralHistoryService } from 'app/entities/funeral-history/funeral-history.service';
import { FuneralHistory } from 'app/shared/model/funeral-history.model';

describe('Component Tests', () => {
  describe('FuneralHistory Management Update Component', () => {
    let comp: FuneralHistoryUpdateComponent;
    let fixture: ComponentFixture<FuneralHistoryUpdateComponent>;
    let service: FuneralHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FuneralHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FuneralHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuneralHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuneralHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FuneralHistory(123);
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
        const entity = new FuneralHistory();
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
