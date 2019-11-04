import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { CaseFileDeleteDialogComponent } from 'app/entities/case-file/case-file-delete-dialog.component';
import { CaseFileService } from 'app/entities/case-file/case-file.service';

describe('Component Tests', () => {
  describe('CaseFile Management Delete Component', () => {
    let comp: CaseFileDeleteDialogComponent;
    let fixture: ComponentFixture<CaseFileDeleteDialogComponent>;
    let service: CaseFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [CaseFileDeleteDialogComponent]
      })
        .overrideTemplate(CaseFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CaseFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CaseFileService);
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
