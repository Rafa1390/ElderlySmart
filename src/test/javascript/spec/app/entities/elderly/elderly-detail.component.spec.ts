import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { ElderlyDetailComponent } from 'app/entities/elderly/elderly-detail.component';
import { Elderly } from 'app/shared/model/elderly.model';

describe('Component Tests', () => {
  describe('Elderly Management Detail Component', () => {
    let comp: ElderlyDetailComponent;
    let fixture: ComponentFixture<ElderlyDetailComponent>;
    const route = ({ data: of({ elderly: new Elderly(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ElderlyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ElderlyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ElderlyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.elderly).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
