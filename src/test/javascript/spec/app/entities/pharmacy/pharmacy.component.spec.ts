import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { PharmacyComponent } from 'app/entities/pharmacy/pharmacy.component';
import { PharmacyService } from 'app/entities/pharmacy/pharmacy.service';
import { Pharmacy } from 'app/shared/model/pharmacy.model';

describe('Component Tests', () => {
  describe('Pharmacy Management Component', () => {
    let comp: PharmacyComponent;
    let fixture: ComponentFixture<PharmacyComponent>;
    let service: PharmacyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PharmacyComponent],
        providers: []
      })
        .overrideTemplate(PharmacyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PharmacyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PharmacyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pharmacy(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pharmacies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
