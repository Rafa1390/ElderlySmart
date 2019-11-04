import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { MortuaryComponent } from 'app/entities/mortuary/mortuary.component';
import { MortuaryService } from 'app/entities/mortuary/mortuary.service';
import { Mortuary } from 'app/shared/model/mortuary.model';

describe('Component Tests', () => {
  describe('Mortuary Management Component', () => {
    let comp: MortuaryComponent;
    let fixture: ComponentFixture<MortuaryComponent>;
    let service: MortuaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MortuaryComponent],
        providers: []
      })
        .overrideTemplate(MortuaryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MortuaryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MortuaryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Mortuary(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mortuaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
