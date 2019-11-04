import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { ProviderHistoryDetailComponent } from 'app/entities/provider-history/provider-history-detail.component';
import { ProviderHistory } from 'app/shared/model/provider-history.model';

describe('Component Tests', () => {
  describe('ProviderHistory Management Detail Component', () => {
    let comp: ProviderHistoryDetailComponent;
    let fixture: ComponentFixture<ProviderHistoryDetailComponent>;
    const route = ({ data: of({ providerHistory: new ProviderHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ProviderHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProviderHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProviderHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.providerHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
