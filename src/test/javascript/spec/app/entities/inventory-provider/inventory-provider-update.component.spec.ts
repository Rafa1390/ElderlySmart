import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { InventoryProviderUpdateComponent } from 'app/entities/inventory-provider/inventory-provider-update.component';
import { InventoryProviderService } from 'app/entities/inventory-provider/inventory-provider.service';
import { InventoryProvider } from 'app/shared/model/inventory-provider.model';

describe('Component Tests', () => {
  describe('InventoryProvider Management Update Component', () => {
    let comp: InventoryProviderUpdateComponent;
    let fixture: ComponentFixture<InventoryProviderUpdateComponent>;
    let service: InventoryProviderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [InventoryProviderUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InventoryProviderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryProviderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryProviderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryProvider(123);
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
        const entity = new InventoryProvider();
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
