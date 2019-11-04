import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { RecreationalActivityDeleteDialogComponent } from 'app/entities/recreational-activity/recreational-activity-delete-dialog.component';
import { RecreationalActivityService } from 'app/entities/recreational-activity/recreational-activity.service';

describe('Component Tests', () => {
  describe('RecreationalActivity Management Delete Component', () => {
    let comp: RecreationalActivityDeleteDialogComponent;
    let fixture: ComponentFixture<RecreationalActivityDeleteDialogComponent>;
    let service: RecreationalActivityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [RecreationalActivityDeleteDialogComponent]
      })
        .overrideTemplate(RecreationalActivityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecreationalActivityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecreationalActivityService);
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
