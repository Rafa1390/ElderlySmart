import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { ElderlyDeleteDialogComponent } from 'app/entities/elderly/elderly-delete-dialog.component';
import { ElderlyService } from 'app/entities/elderly/elderly.service';

describe('Component Tests', () => {
  describe('Elderly Management Delete Component', () => {
    let comp: ElderlyDeleteDialogComponent;
    let fixture: ComponentFixture<ElderlyDeleteDialogComponent>;
    let service: ElderlyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [ElderlyDeleteDialogComponent]
      })
        .overrideTemplate(ElderlyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ElderlyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElderlyService);
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
