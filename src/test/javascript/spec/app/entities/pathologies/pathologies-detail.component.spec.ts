import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { PathologiesDetailComponent } from 'app/entities/pathologies/pathologies-detail.component';
import { Pathologies } from 'app/shared/model/pathologies.model';

describe('Component Tests', () => {
  describe('Pathologies Management Detail Component', () => {
    let comp: PathologiesDetailComponent;
    let fixture: ComponentFixture<PathologiesDetailComponent>;
    const route = ({ data: of({ pathologies: new Pathologies(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PathologiesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PathologiesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PathologiesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pathologies).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
