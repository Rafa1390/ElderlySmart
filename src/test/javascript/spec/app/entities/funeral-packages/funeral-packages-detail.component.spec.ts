import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { FuneralPackagesDetailComponent } from 'app/entities/funeral-packages/funeral-packages-detail.component';
import { FuneralPackages } from 'app/shared/model/funeral-packages.model';

describe('Component Tests', () => {
  describe('FuneralPackages Management Detail Component', () => {
    let comp: FuneralPackagesDetailComponent;
    let fixture: ComponentFixture<FuneralPackagesDetailComponent>;
    const route = ({ data: of({ funeralPackages: new FuneralPackages(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FuneralPackagesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FuneralPackagesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuneralPackagesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.funeralPackages).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
