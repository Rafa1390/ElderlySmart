import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { ProviderHistoryDeleteDialogComponent } from 'app/entities/provider-history/provider-history-delete-dialog.component';
import { ProviderHistoryService } from 'app/entities/provider-history/provider-history.service';

describe('Component Tests', () => {
  describe('ProviderHistory Management Delete Component', () => {
    let comp: ProviderHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<ProviderHistoryDeleteDialogComponent>;
    let service: ProviderHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ProviderHistoryDeleteDialogComponent]
      })
        .overrideTemplate(ProviderHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProviderHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProviderHistoryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
