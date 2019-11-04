import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ElderlySmartTestModule } from '../../../test.module';
import { FuneralPackagesDeleteDialogComponent } from 'app/entities/funeral-packages/funeral-packages-delete-dialog.component';
import { FuneralPackagesService } from 'app/entities/funeral-packages/funeral-packages.service';

describe('Component Tests', () => {
  describe('FuneralPackages Management Delete Component', () => {
    let comp: FuneralPackagesDeleteDialogComponent;
    let fixture: ComponentFixture<FuneralPackagesDeleteDialogComponent>;
    let service: FuneralPackagesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ElderlySmartTestModule],
        declarations: [FuneralPackagesDeleteDialogComponent]
      })
        .overrideTemplate(FuneralPackagesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuneralPackagesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuneralPackagesService);
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
