import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { AllergiesDetailComponent } from 'app/entities/allergies/allergies-detail.component';
import { Allergies } from 'app/shared/model/allergies.model';

describe('Component Tests', () => {
  describe('Allergies Management Detail Component', () => {
    let comp: AllergiesDetailComponent;
    let fixture: ComponentFixture<AllergiesDetailComponent>;
    const route = ({ data: of({ allergies: new Allergies(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AllergiesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllergiesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllergiesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allergies).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
