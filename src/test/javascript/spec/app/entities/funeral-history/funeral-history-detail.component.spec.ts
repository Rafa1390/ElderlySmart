import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { FuneralHistoryDetailComponent } from 'app/entities/funeral-history/funeral-history-detail.component';
import { FuneralHistory } from 'app/shared/model/funeral-history.model';

describe('Component Tests', () => {
  describe('FuneralHistory Management Detail Component', () => {
    let comp: FuneralHistoryDetailComponent;
    let fixture: ComponentFixture<FuneralHistoryDetailComponent>;
    const route = ({ data: of({ funeralHistory: new FuneralHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FuneralHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FuneralHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuneralHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.funeralHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
