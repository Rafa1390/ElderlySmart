import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { DonationHistoryDeleteDialogComponent } from 'app/entities/donation-history/donation-history-delete-dialog.component';
import { DonationHistoryService } from 'app/entities/donation-history/donation-history.service';

describe('Component Tests', () => {
  describe('DonationHistory Management Delete Component', () => {
    let comp: DonationHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<DonationHistoryDeleteDialogComponent>;
    let service: DonationHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [DonationHistoryDeleteDialogComponent]
      })
        .overrideTemplate(DonationHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DonationHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DonationHistoryService);
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
