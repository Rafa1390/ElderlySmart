import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { AllergiesComponent } from 'app/entities/allergies/allergies.component';
import { AllergiesService } from 'app/entities/allergies/allergies.service';
import { Allergies } from 'app/shared/model/allergies.model';

describe('Component Tests', () => {
  describe('Allergies Management Component', () => {
    let comp: AllergiesComponent;
    let fixture: ComponentFixture<AllergiesComponent>;
    let service: AllergiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [AllergiesComponent],
        providers: []
      })
        .overrideTemplate(AllergiesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllergiesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllergiesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Allergies(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.allergies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
