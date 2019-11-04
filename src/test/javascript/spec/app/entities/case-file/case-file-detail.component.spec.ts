import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ElderlySmartTestModule } from '../../../test.module';
import { CaseFileDetailComponent } from 'app/entities/case-file/case-file-detail.component';
import { CaseFile } from 'app/shared/model/case-file.model';

describe('Component Tests', () => {
  describe('CaseFile Management Detail Component', () => {
    let comp: CaseFileDetailComponent;
    let fixture: ComponentFixture<CaseFileDetailComponent>;
    const route = ({ data: of({ caseFile: new CaseFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [CaseFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CaseFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CaseFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.caseFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
