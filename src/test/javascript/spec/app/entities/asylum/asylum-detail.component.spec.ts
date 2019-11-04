import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { AsylumDetailComponent } from 'app/entities/asylum/asylum-detail.component';
import { Asylum } from 'app/shared/model/asylum.model';

describe('Component Tests', () => {
  describe('Asylum Management Detail Component', () => {
    let comp: AsylumDetailComponent;
    let fixture: ComponentFixture<AsylumDetailComponent>;
    const route = ({ data: of({ asylum: new Asylum(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AsylumDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AsylumDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AsylumDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.asylum).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
