import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { RecreationalActivityComponent } from 'app/entities/recreational-activity/recreational-activity.component';
import { RecreationalActivityService } from 'app/entities/recreational-activity/recreational-activity.service';
import { RecreationalActivity } from 'app/shared/model/recreational-activity.model';

describe('Component Tests', () => {
  describe('RecreationalActivity Management Component', () => {
    let comp: RecreationalActivityComponent;
    let fixture: ComponentFixture<RecreationalActivityComponent>;
    let service: RecreationalActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [RecreationalActivityComponent],
        providers: []
      })
        .overrideTemplate(RecreationalActivityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecreationalActivityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecreationalActivityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RecreationalActivity(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.recreationalActivities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
