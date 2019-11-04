import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { FuneralHistoryComponent } from 'app/entities/funeral-history/funeral-history.component';
import { FuneralHistoryService } from 'app/entities/funeral-history/funeral-history.service';
import { FuneralHistory } from 'app/shared/model/funeral-history.model';

describe('Component Tests', () => {
  describe('FuneralHistory Management Component', () => {
    let comp: FuneralHistoryComponent;
    let fixture: ComponentFixture<FuneralHistoryComponent>;
    let service: FuneralHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FuneralHistoryComponent],
        providers: []
      })
        .overrideTemplate(FuneralHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuneralHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuneralHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FuneralHistory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.funeralHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
