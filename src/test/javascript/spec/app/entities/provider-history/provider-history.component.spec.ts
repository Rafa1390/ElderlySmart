import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ElderlySmartTestModule } from '../../../test.module';
import { ProviderHistoryComponent } from 'app/entities/provider-history/provider-history.component';
import { ProviderHistoryService } from 'app/entities/provider-history/provider-history.service';
import { ProviderHistory } from 'app/shared/model/provider-history.model';

describe('Component Tests', () => {
  describe('ProviderHistory Management Component', () => {
    let comp: ProviderHistoryComponent;
    let fixture: ComponentFixture<ProviderHistoryComponent>;
    let service: ProviderHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ProviderHistoryComponent],
        providers: []
      })
        .overrideTemplate(ProviderHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProviderHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProviderHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProviderHistory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.providerHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
