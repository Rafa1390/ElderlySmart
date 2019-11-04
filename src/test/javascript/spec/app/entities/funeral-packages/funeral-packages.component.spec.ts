import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { FuneralPackagesComponent } from 'app/entities/funeral-packages/funeral-packages.component';
import { FuneralPackagesService } from 'app/entities/funeral-packages/funeral-packages.service';
import { FuneralPackages } from 'app/shared/model/funeral-packages.model';

describe('Component Tests', () => {
  describe('FuneralPackages Management Component', () => {
    let comp: FuneralPackagesComponent;
    let fixture: ComponentFixture<FuneralPackagesComponent>;
    let service: FuneralPackagesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FuneralPackagesComponent],
        providers: []
      })
        .overrideTemplate(FuneralPackagesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuneralPackagesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuneralPackagesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FuneralPackages(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.funeralPackages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
