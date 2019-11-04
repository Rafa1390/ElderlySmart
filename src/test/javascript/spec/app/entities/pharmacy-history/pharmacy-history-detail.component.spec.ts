import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { PharmacyHistoryDetailComponent } from 'app/entities/pharmacy-history/pharmacy-history-detail.component';
import { PharmacyHistory } from 'app/shared/model/pharmacy-history.model';

describe('Component Tests', () => {
  describe('PharmacyHistory Management Detail Component', () => {
    let comp: PharmacyHistoryDetailComponent;
    let fixture: ComponentFixture<PharmacyHistoryDetailComponent>;
    const route = ({ data: of({ pharmacyHistory: new PharmacyHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PharmacyHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PharmacyHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PharmacyHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pharmacyHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
