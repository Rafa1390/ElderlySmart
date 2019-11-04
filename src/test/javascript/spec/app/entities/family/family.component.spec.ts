import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { FamilyComponent } from 'app/entities/family/family.component';
import { FamilyService } from 'app/entities/family/family.service';
import { Family } from 'app/shared/model/family.model';

describe('Component Tests', () => {
  describe('Family Management Component', () => {
    let comp: FamilyComponent;
    let fixture: ComponentFixture<FamilyComponent>;
    let service: FamilyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FamilyComponent],
        providers: []
      })
        .overrideTemplate(FamilyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FamilyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamilyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Family(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.families[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
