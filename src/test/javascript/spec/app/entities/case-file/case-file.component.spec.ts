import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { CaseFileComponent } from 'app/entities/case-file/case-file.component';
import { CaseFileService } from 'app/entities/case-file/case-file.service';
import { CaseFile } from 'app/shared/model/case-file.model';

describe('Component Tests', () => {
  describe('CaseFile Management Component', () => {
    let comp: CaseFileComponent;
    let fixture: ComponentFixture<CaseFileComponent>;
    let service: CaseFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [CaseFileComponent],
        providers: []
      })
        .overrideTemplate(CaseFileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CaseFileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CaseFileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CaseFile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.caseFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
