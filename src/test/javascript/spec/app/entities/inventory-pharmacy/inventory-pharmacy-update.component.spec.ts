import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { InventoryPharmacyUpdateComponent } from 'app/entities/inventory-pharmacy/inventory-pharmacy-update.component';
import { InventoryPharmacyService } from 'app/entities/inventory-pharmacy/inventory-pharmacy.service';
import { InventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';

describe('Component Tests', () => {
  describe('InventoryPharmacy Management Update Component', () => {
    let comp: InventoryPharmacyUpdateComponent;
    let fixture: ComponentFixture<InventoryPharmacyUpdateComponent>;
    let service: InventoryPharmacyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [InventoryPharmacyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InventoryPharmacyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryPharmacyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryPharmacyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryPharmacy(123);
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
        const entity = new InventoryPharmacy();
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
