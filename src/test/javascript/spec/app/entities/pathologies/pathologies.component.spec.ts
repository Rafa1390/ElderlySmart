import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { PathologiesComponent } from 'app/entities/pathologies/pathologies.component';
import { PathologiesService } from 'app/entities/pathologies/pathologies.service';
import { Pathologies } from 'app/shared/model/pathologies.model';

describe('Component Tests', () => {
  describe('Pathologies Management Component', () => {
    let comp: PathologiesComponent;
    let fixture: ComponentFixture<PathologiesComponent>;
    let service: PathologiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [PathologiesComponent],
        providers: []
      })
        .overrideTemplate(PathologiesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PathologiesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PathologiesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pathologies(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pathologies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
