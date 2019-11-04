import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { CleaningProgramComponent } from 'app/entities/cleaning-program/cleaning-program.component';
import { CleaningProgramService } from 'app/entities/cleaning-program/cleaning-program.service';
import { CleaningProgram } from 'app/shared/model/cleaning-program.model';

describe('Component Tests', () => {
  describe('CleaningProgram Management Component', () => {
    let comp: CleaningProgramComponent;
    let fixture: ComponentFixture<CleaningProgramComponent>;
    let service: CleaningProgramService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [CleaningProgramComponent],
        providers: []
      })
        .overrideTemplate(CleaningProgramComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CleaningProgramComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CleaningProgramService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CleaningProgram(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cleaningPrograms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
