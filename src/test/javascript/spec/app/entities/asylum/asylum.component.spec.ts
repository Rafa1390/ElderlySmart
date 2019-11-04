import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { AsylumComponent } from 'app/entities/asylum/asylum.component';
import { AsylumService } from 'app/entities/asylum/asylum.service';
import { Asylum } from 'app/shared/model/asylum.model';

describe('Component Tests', () => {
  describe('Asylum Management Component', () => {
    let comp: AsylumComponent;
    let fixture: ComponentFixture<AsylumComponent>;
    let service: AsylumService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AsylumComponent],
        providers: []
      })
        .overrideTemplate(AsylumComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AsylumComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AsylumService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Asylum(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.asylums[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
