import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { InventoryProviderDetailComponent } from 'app/entities/inventory-provider/inventory-provider-detail.component';
import { InventoryProvider } from 'app/shared/model/inventory-provider.model';

describe('Component Tests', () => {
  describe('InventoryProvider Management Detail Component', () => {
    let comp: InventoryProviderDetailComponent;
    let fixture: ComponentFixture<InventoryProviderDetailComponent>;
    const route = ({ data: of({ inventoryProvider: new InventoryProvider(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [InventoryProviderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InventoryProviderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryProviderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryProvider).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
