import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { ProviderHistoryUpdateComponent } from 'app/entities/provider-history/provider-history-update.component';
import { ProviderHistoryService } from 'app/entities/provider-history/provider-history.service';
import { ProviderHistory } from 'app/shared/model/provider-history.model';

describe('Component Tests', () => {
  describe('ProviderHistory Management Update Component', () => {
    let comp: ProviderHistoryUpdateComponent;
    let fixture: ComponentFixture<ProviderHistoryUpdateComponent>;
    let service: ProviderHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ProviderHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProviderHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProviderHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProviderHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProviderHistory(123);
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
        const entity = new ProviderHistory();
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
