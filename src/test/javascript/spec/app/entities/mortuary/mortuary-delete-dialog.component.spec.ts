import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { MortuaryDeleteDialogComponent } from 'app/entities/mortuary/mortuary-delete-dialog.component';
import { MortuaryService } from 'app/entities/mortuary/mortuary.service';

describe('Component Tests', () => {
  describe('Mortuary Management Delete Component', () => {
    let comp: MortuaryDeleteDialogComponent;
    let fixture: ComponentFixture<MortuaryDeleteDialogComponent>;
    let service: MortuaryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [MortuaryDeleteDialogComponent]
      })
        .overrideTemplate(MortuaryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MortuaryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MortuaryService);
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
