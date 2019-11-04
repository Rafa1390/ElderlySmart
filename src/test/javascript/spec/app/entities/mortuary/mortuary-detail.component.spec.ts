import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { MortuaryDetailComponent } from 'app/entities/mortuary/mortuary-detail.component';
import { Mortuary } from 'app/shared/model/mortuary.model';

describe('Component Tests', () => {
  describe('Mortuary Management Detail Component', () => {
    let comp: MortuaryDetailComponent;
    let fixture: ComponentFixture<MortuaryDetailComponent>;
    const route = ({ data: of({ mortuary: new Mortuary(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MortuaryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MortuaryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MortuaryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mortuary).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
