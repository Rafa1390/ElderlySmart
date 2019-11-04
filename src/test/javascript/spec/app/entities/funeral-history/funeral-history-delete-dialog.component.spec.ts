import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { FuneralHistoryDeleteDialogComponent } from 'app/entities/funeral-history/funeral-history-delete-dialog.component';
import { FuneralHistoryService } from 'app/entities/funeral-history/funeral-history.service';

describe('Component Tests', () => {
  describe('FuneralHistory Management Delete Component', () => {
    let comp: FuneralHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<FuneralHistoryDeleteDialogComponent>;
    let service: FuneralHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FuneralHistoryDeleteDialogComponent]
      })
        .overrideTemplate(FuneralHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuneralHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuneralHistoryService);
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
