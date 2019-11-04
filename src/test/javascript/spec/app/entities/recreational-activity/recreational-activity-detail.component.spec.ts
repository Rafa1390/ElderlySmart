import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { RecreationalActivityDetailComponent } from 'app/entities/recreational-activity/recreational-activity-detail.component';
import { RecreationalActivity } from 'app/shared/model/recreational-activity.model';

describe('Component Tests', () => {
  describe('RecreationalActivity Management Detail Component', () => {
    let comp: RecreationalActivityDetailComponent;
    let fixture: ComponentFixture<RecreationalActivityDetailComponent>;
    const route = ({ data: of({ recreationalActivity: new RecreationalActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [RecreationalActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RecreationalActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecreationalActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recreationalActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
