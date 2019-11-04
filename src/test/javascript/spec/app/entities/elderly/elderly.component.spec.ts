import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { ElderlyComponent } from 'app/entities/elderly/elderly.component';
import { ElderlyService } from 'app/entities/elderly/elderly.service';
import { Elderly } from 'app/shared/model/elderly.model';

describe('Component Tests', () => {
  describe('Elderly Management Component', () => {
    let comp: ElderlyComponent;
    let fixture: ComponentFixture<ElderlyComponent>;
    let service: ElderlyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ElderlyComponent],
        providers: []
      })
        .overrideTemplate(ElderlyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElderlyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElderlyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Elderly(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.elderlies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
