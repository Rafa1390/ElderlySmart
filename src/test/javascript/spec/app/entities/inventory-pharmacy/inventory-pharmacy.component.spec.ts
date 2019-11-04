import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { InventoryPharmacyComponent } from 'app/entities/inventory-pharmacy/inventory-pharmacy.component';
import { InventoryPharmacyService } from 'app/entities/inventory-pharmacy/inventory-pharmacy.service';
import { InventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';

describe('Component Tests', () => {
  describe('InventoryPharmacy Management Component', () => {
    let comp: InventoryPharmacyComponent;
    let fixture: ComponentFixture<InventoryPharmacyComponent>;
    let service: InventoryPharmacyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [InventoryPharmacyComponent],
        providers: []
      })
        .overrideTemplate(InventoryPharmacyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryPharmacyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryPharmacyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InventoryPharmacy(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inventoryPharmacies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
