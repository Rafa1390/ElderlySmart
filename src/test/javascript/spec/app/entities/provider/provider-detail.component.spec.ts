import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { ProviderDetailComponent } from 'app/entities/provider/provider-detail.component';
import { Provider } from 'app/shared/model/provider.model';

describe('Component Tests', () => {
  describe('Provider Management Detail Component', () => {
    let comp: ProviderDetailComponent;
    let fixture: ComponentFixture<ProviderDetailComponent>;
    const route = ({ data: of({ provider: new Provider(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ProviderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProviderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProviderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.provider).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
