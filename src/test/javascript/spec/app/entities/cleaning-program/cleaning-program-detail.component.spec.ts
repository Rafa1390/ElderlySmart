import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { CleaningProgramDetailComponent } from 'app/entities/cleaning-program/cleaning-program-detail.component';
import { CleaningProgram } from 'app/shared/model/cleaning-program.model';

describe('Component Tests', () => {
  describe('CleaningProgram Management Detail Component', () => {
    let comp: CleaningProgramDetailComponent;
    let fixture: ComponentFixture<CleaningProgramDetailComponent>;
    const route = ({ data: of({ cleaningProgram: new CleaningProgram(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [CleaningProgramDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CleaningProgramDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CleaningProgramDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cleaningProgram).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
