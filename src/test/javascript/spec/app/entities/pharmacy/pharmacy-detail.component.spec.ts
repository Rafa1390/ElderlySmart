import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { PharmacyDetailComponent } from 'app/entities/pharmacy/pharmacy-detail.component';
import { Pharmacy } from 'app/shared/model/pharmacy.model';

describe('Component Tests', () => {
  describe('Pharmacy Management Detail Component', () => {
    let comp: PharmacyDetailComponent;
    let fixture: ComponentFixture<PharmacyDetailComponent>;
    const route = ({ data: of({ pharmacy: new Pharmacy(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PharmacyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PharmacyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PharmacyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pharmacy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
