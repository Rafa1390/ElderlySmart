import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { InventoryPharmacyDetailComponent } from 'app/entities/inventory-pharmacy/inventory-pharmacy-detail.component';
import { InventoryPharmacy } from 'app/shared/model/inventory-pharmacy.model';

describe('Component Tests', () => {
  describe('InventoryPharmacy Management Detail Component', () => {
    let comp: InventoryPharmacyDetailComponent;
    let fixture: ComponentFixture<InventoryPharmacyDetailComponent>;
    const route = ({ data: of({ inventoryPharmacy: new InventoryPharmacy(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [InventoryPharmacyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InventoryPharmacyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryPharmacyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryPharmacy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
