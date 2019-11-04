import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { CleaningProgramDeleteDialogComponent } from 'app/entities/cleaning-program/cleaning-program-delete-dialog.component';
import { CleaningProgramService } from 'app/entities/cleaning-program/cleaning-program.service';

describe('Component Tests', () => {
  describe('CleaningProgram Management Delete Component', () => {
    let comp: CleaningProgramDeleteDialogComponent;
    let fixture: ComponentFixture<CleaningProgramDeleteDialogComponent>;
    let service: CleaningProgramService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [CleaningProgramDeleteDialogComponent]
      })
        .overrideTemplate(CleaningProgramDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CleaningProgramDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CleaningProgramService);
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
